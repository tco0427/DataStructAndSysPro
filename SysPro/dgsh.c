/* dgsh.c: my shell implementation, by tco0427. a29661498@gmail.com */

/*
과목명(분반): 시스템프로그래밍(1분반)
교수명: 최 종 무 교수님
학번: 32180472
이름: 김동규
학과: 컴퓨터공학과
제출일: 2020년 10월 29일
*/

#include <stdio.h>	//표준 입출력 라이브러리
#include <stdlib.h>	//표준 라이브러리
#include <unistd.h>	//유닉스표준 라이브러리
#include <stdbool.h>	//bool자료형을 위한 라이브러리
#include <string.h>	//strtok등과 같은 문자열관련 함수들을 포함하기 위한 라이브러리
#include <errno.h>	//에러넘버가 정의된 라이브러리(errno)
#include <fcntl.h>	//함수컨트롤 라이브러리

void help();
bool run(char* line);
int tokenize(char* buf,char* delimit,char* tokens[],int maxTokens);
void changeDir(char* targetDir[]);

int main(){
	char line[1024];

	while(1){
		printf("%s$ ",get_current_dir_name());
		fgets(line,sizeof(line)-1,stdin);
		if(run(line)==false){
			break;
		}
	}
	return 0;
}

void help(){
	printf("/----------DongGyu_Shell----------/\n");
	printf("How to use dgshell\n");
	printf("?\t: show this help\n");
	printf("help\t: show this help\n");
	printf("exit\t: exit this shell\n");
	printf("quit\t: quit this shell\n");
	printf("&:\t: background processing\n");
	printf(">:\t: redirection\n");
	printf("|:\t: pipe(Just once)\n");
	printf("/---------------------------------/\n");
}


bool run(char* line){
	char *tokenArray[128];
	int count;
	int tokenCount=tokenize(line," \n",tokenArray,(sizeof(tokenArray)/sizeof(char *)));
	
	//Advanced commnad들이 요청되었는지를 체크하기 위한 플래그 용도로 변수를 선언하였다.
	bool background_flag=false;
	bool redirection_flag=false;
	bool pipe_flag=false;
	
	//pipe와 redirection, "|" 와 ">" 토큰 위치를 NULL로 변경해주기 위해 다음 변수들을 사용하였다.
	int redirectionTokenNumber=-1;
	int pipeTokenNumber=-1;
	
	pid_t fork_return;
	
	//file descriptor부분
	int fd;
	int fdpipe[2];

	//pipe시에 앞과 뒤 명령어를 구분해줄 것인데 이를 위한 문자열배열
        char *pipeBuffer1[10];
        char *pipeBuffer2[10];

        int i=0,col=0;

	if(tokenCount==0){
		return true;
	}else if((strcmp(tokenArray[0],"help")==0)||(strcmp(tokenArray[0],"?")==0)){
		help();
		return true;
	}else if((strcmp(tokenArray[0],"exit")==0)||(strcmp(tokenArray[0],"quit")==0)){
		return false;
	}else if((strcmp(tokenArray[0],"cd"))==0 && tokenCount==2){
		changeDir(tokenArray);
		return true;
	}

	for(count=0;count<tokenCount;count++){
		if(strcmp(tokenArray[count],"&")==0){
			background_flag=true;
			tokenArray[count]=NULL;
		}else if(strcmp(tokenArray[count],">")==0){
			redirection_flag=true;
			tokenArray[count]=NULL;
			redirectionTokenNumber=count+1;
		}else if(strcmp(tokenArray[count],"|")==0){		
			pipe_flag=true;
			pipeTokenNumber=count;
		}
	}
	if((fork_return=fork())<0){
		perror("fork error");
		printf("dgsh를 종료합니다.\n");
		exit(1);
	}else if(fork_return==0){
		if(pipe_flag==true){
			 for(i=0;i!=pipeTokenNumber;i++){
				 pipeBuffer1[col]=tokenArray[i];
				 col++;
			}
			 tokenArray[pipeTokenNumber]=NULL;
      			 pipeBuffer1[col]=NULL;
       			 i++;
     			 col=0;
       			 while(tokenArray[i]!=NULL){
				pipeBuffer2[col]=tokenArray[i];
				col++; i++;
       			 }
       			 pipeBuffer2[col]=NULL;
		}
		if(redirection_flag==true){
			if((fd=open(tokenArray[redirectionTokenNumber],O_RDWR|O_CREAT,0664))<0){
				printf("Error occurred during 'open' system call, errno=%d\n",errno);
				exit(1);
			}
			//exec이전에 표준출력(STDOUT_FILENO)을 file의 fd로 duplicate시킴으로써
			//STDOUT이 터미널이 아닌 어떤 파일을
			//가리키도록 redirection구현
			dup2(fd,1);

			//파일을 open하면 close해주는 것이 옳다.
			//따라서 open이 정상적으로 실행되었다면
			//duplicate를 통해 표준출력이 fd와 같은 inode를 가리키게 될 것이다.
			//따라서 duplicate이후에 fd를 close하더라도 표준출력은 fd가 가리키던 inode와 같은
			//inode를 기억하고 있으므로 fd를 close해주는 과정을 통해 open이후 close를 수행할 수 있게 하였다.
			close(fd);
		}


		if(pipe_flag==false){
			execvp(tokenArray[0],tokenArray);
			exit(1);
		}else{
			pipe(fdpipe);
			if(fork()==0){
				close(fdpipe[0]);
				dup2(fdpipe[1],1);
				close(fdpipe[1]);
				execvp(pipeBuffer1[0],pipeBuffer1);
				exit(1);
			}else{
				close(fdpipe[1]);
				dup2(fdpipe[0],0);
				close(fdpipe[0]);
				execvp(pipeBuffer2[0],pipeBuffer2);
				exit(1);
			}
		}
	}else{
		//백그라운드 플래그 역할을 하는bool자료형 background_flag에 따라
		//부모프로세스가 자식프로세스의 수행이 종료될 때까지 대기할 것인지 말 것인지에 대해서
		//if문을 통해 분기한다.
		if(background_flag==false){
			wait();
		}
	}

	return true;
}
	
int tokenize(char* buf, char* delimit, char* tokens[], int maxTokens){
	int tokenCount=0;
	char* token;
	token=strtok(buf,delimit);
	while(token!=NULL&&tokenCount<maxTokens){
		tokens[tokenCount]=token;
		token=strtok(NULL,delimit);
		tokenCount++;
	}
	tokens[tokenCount]=NULL;
	return tokenCount;
}

void changeDir(char* targetDir[]){
        char *path;
        char buf[100];
        //현재 작업디렉토리의 이름을 size만큼 길이로 buf에 복사한다.
        //char *getcwd(cahr *buf, size_t size);
        //실패시 NULL 반환
        path=getcwd(buf,sizeof(buf));
        if(path!=NULL){
                //이동할 디렉토리 이름을 뒤에 붙여준다.
                path=strcat(path,"/");
                path=strcat(path,targetDir[1]);
                //chdir함수를 통해 현재 작업 디렉토리를 변경하여준다.
                chdir(path);
        }
}

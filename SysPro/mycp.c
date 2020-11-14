/* mycp.c: my copy program, by tco0427. a29661498@gmail.com */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <sys/stat.h>

#define MAX_BUF 32

int main(int argc, char *argv[]){
	int fdr, fdw, read_size, write_size;
	char buf[MAX_BUF];
	struct stat st;	

	if(argc!=3){
		printf("USAGE: %s source_file_name destination_file_name\n",argv[0]);
		exit(-1);
	}
	
	fdr=open(argv[1],O_RDONLY);
	if(fdr<0){
		printf("Can't open %s file with errno %d\n",argv[1],errno);
		exit(-1);
	}

	stat(argv[1],&st);

	fdw=open(argv[2],O_RDWR|O_CREAT|O_EXCL,st.st_mode);
	
	if(fdw<0){
		printf("Can't open %s file with errno %d\n",argv[2],errno);
		exit(-1);
	}

	while(1){
		read_size=read(fdr,buf,MAX_BUF);
		if(read_size==0){
			break;
		}
		write_size=write(fdw,buf,read_size);
	}
	close(fdr);
	close(fdw);
	
	return 0;
}	

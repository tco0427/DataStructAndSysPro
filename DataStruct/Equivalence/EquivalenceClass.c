/*
Equivalence Class(동치부류,동치클래스) 구현
과목명(분반): 자료구조(1분반)
교수명: 나연묵 교수님
학번: 32180472
학과: 컴퓨터공학과
이름: 김동규
제출일: 2020/10/16
 */

#include <stdio.h>
#include <stdlib.h>

typedef struct node{
    int data;
    struct node *link;
}NODE;

void equivalence(int m,int n);

int main(){
    int n,m;
    printf("Enter the number of equivalence pairs>> ");
    scanf("%d",&n);
    printf("Enter the number of variables>>");
    scanf("%d",&m);
    equivalence(n, m);
    
    return 0;
}

void equivalence(int m,int n){
    int *BIT=(int *)malloc(n*sizeof(int));
    NODE *SEQ=(NODE *)malloc(n*sizeof(int));
    
    int i,k,av;
    int buf_i,buf_j,index;

    NODE *nodeArray=(NODE *)calloc((2*m),sizeof(NODE));
    NODE *top;
    NODE *ptr;
    NODE *temp;
    
    if(SEQ==NULL || BIT==NULL||nodeArray==NULL){
        printf("Memory Error\n");
        exit(1);
    }
    
    for(i=0;i<n;i++){
        SEQ[i].link=NULL;
        BIT[i]=0;
    }
    av=0;
    for(k=0;k<m;k++){
        printf("enter (i,j) pair >>");
        scanf("%d %d",&buf_i,&buf_j);
        if(buf_i<1||buf_i>n){
            printf("input error");
            exit(1);
        }else if(buf_j<1||buf_j>n){
            printf("input error");
            exit(1);
        }
        nodeArray[av].data=buf_j;
        nodeArray[av].link=SEQ[buf_i-1].link;
        SEQ[buf_i-1].link=&nodeArray[av]; av=av+1;
        
        nodeArray[av].data=buf_i;
        nodeArray[av].link=SEQ[buf_j-1].link;
        SEQ[buf_j-1].link=&nodeArray[av]; av=av+1;
    }
    
    index=0;
    while(index<n){
        if(BIT[index]==0){
            printf("A new class: %d ",index+1);
            BIT[index]=1;
            ptr=SEQ[index].link; top=NULL;
            while(1){
                while(ptr!=NULL){
                    buf_j=ptr->data;
                    if(BIT[buf_j-1]==0){
                        printf("%d ",buf_j);
                        BIT[buf_j-1]=1;
                        temp=ptr->link;
                        ptr->link=top;
                        top=ptr;
                        ptr=temp;
                    }else{
                        ptr=ptr->link;
                    }
                }
                if(top==NULL){
                    break;
                }
                ptr=SEQ[((top->data)-1)].link;
                top=top->link;
            }
            printf("\n");
        }
        index=index+1;
    }
}

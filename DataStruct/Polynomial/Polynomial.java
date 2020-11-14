/*
다항식 덧셈 구현
과목명(분반): 자료구조(1분반)
교수명: 나연묵 교수님
학번: 32180472
학과: 컴퓨터공학과
이름: 김동규
제출일: 2020/09/27
 */
import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    /*
    가장 단순하고 흔한 자료객체 중 하나로 순서가 있는 선형 리스트의 예, '순서리스트(ordered list)'가 있다.
    순서리스트는 순서 정보를 가지는 linear list로 배열(array) 혹은 연결 리스트(linked list)를 통해 구현할 수 있다.
    (메모리 표현 방법이 크게 Array, LinkedList로 구분된다.)
    따라서 Java언어에서 제공하는 ArrayList<E> 배열 기반 자료구조를 이용하여 인스턴스(데이터)를 저장하도록 하였다.
    (내부적으로 배열을 이용하여 인스턴스(데이터)를 저장하고, 인스턴스의 저장 순서를 유지한다는 특성과 동일한 인스턴스의 중복 저장을 허용한다는 특성이 있다.
    또한 필요에 따라 자동적으로 배열의 길이를 스스로 늘리므로 매우 편리하다. 단, 배열의 길이를 늘린다는 것은 내부적으로 더 긴 배열로의 교체를 의미한다.
    이를 통해 사용자가 굉장히 많은 항을 가진 다항식을 입력하더라도 이를 적절히 처리해줄 수 있다.(default capacity는 10이다.))
    이렇게 순서리스트를 표현하는 가장 보편적인 방법인 배열(ArrayList<E>)을 이용하였는데 이는 순차적 사상(sequential mapping)
    즉, ordering(순서)정보를 메모리상의 물리적 인접을 통해 저장하는 방법을 사용한 것이다.
    이 일차원 배열을 이용한 순서리스트(ordered list)를 이용하여 다항식의 덧셈을 구현하였다.
     */
    private List<Integer> orderedList=new ArrayList<>();
    /*
    생성자도 일종의 메소드로 오버로딩의 대상이 된다.
    따라서 인자를 전달받지 않고, 아무런 초기화 작업을 하지 않는({}안이 비어있는) 생성자와 함께
    문자열(String)을 전달받는 생성자, 두 가지 생성자(constructor)를 오버로딩하여 정의하였다.
     */
    //다음의 default constructor는 두 다항식을 더한 결과를 생성하기 위해 정의해두었다.
    Polynomial(){}
    /*
    인자로 문자열을 전달받는 생성자에서는 전달받은 문자열(String)을 parsing(구문분석)하는 작업을 통해
    "x^"와 같이 '덧셈'과정에서 필요없는 문자들을 걸러내고 이를 공백으로 대치하는 작업을 수행하고,
    공백을 기준으로 문자열을 분리(String의 인스턴스 메소드 trim()과 split()을 통해)하고 이를 정수 데이터 타입으로 변환한 후
    인스턴스 변수(orderedList)에 저장하는 과정을 수행한다.
     */
    Polynomial(String str){
        str=str.replace("x^"," ").replace("+"," ").replace("-", " -");
        //인자로 전달받은 문자열이 -가 맨앞에 오는 형태 일 경우 " -"로 제대로 된 결과가 나오지 않아
        //trim()을 통해 문자열 앞 뒤의 공백을 제거하는 과정을 수행하게 되었습니다.
        String[] strings=str.trim().split(" ");
        //분리 과정을 거친 String[]의 내용을 정수 데이터 타입(Integer)로 변환해 저장하기 이전에
        //항수(항의 갯수, 지수-계수 쌍의 갯수)를 첫번째 인덱스에 저장해준다.
        orderedList.add(0,strings.length/2);
        for(int i=0;i<strings.length;i++){
            //try-catch문을 통해 생성자를 통해 전달된 인자가 올바르지 않은 경우를 check하도록 하였다.
            //만약 분리된 문자열을 int형 데이터로 변환하는 과정이 정상적으로 진행되지 않은 경우
            //NumberFormatException예외 인스턴스가 생성되고 catch구문으로 넘어가게 되며
            //사용자에게 이를 알림과 동시에 'System.exit(0);' 문장을 통해 프로그램을 종료하게 된다.
            try {
                //add는 마지막으로 저장된 위치의 다음 인덱스에(ArrayList의 끝에) 인스턴스를 저장합니다.
                //즉,(orderedList.add(i+1,Integer.parseInt(strings[i]));
                //와 동일합니다.
                orderedList.add(Integer.parseInt(strings[i]));
            }catch(NumberFormatException e){
                System.out.println("This input is malformed.");
                System.out.println("Exit the program.");
                System.exit(0);
            }
        }
        //정상적인 작업(catch문을 통해 프로그램이 종료되지 않으면)이 수행된 후에
        //순서리스트는 다항식의 항수와 함께 계수-지수 쌍을 가지게 됩니다.
    }
    //getter와 setter를 정의해주었다.
    //Polynomial클래스 외부에서의 사용은 막기 위해서 private접근지정자를 설정해주었다.
    //(add 메소드(이 메소드는 Polynomial클래스 내부이므로 접근 가능) 내부에서 인자로 전달된 Polynomial인스턴스에 대해서만 사용할 것이고,
    //외부에서 이를 사용하여 순서리스트를 직접 접근하여 조작하는 것은 적절치 않다고 판단하여 private접근지정자를 설정하게 되었다.)
    private List<Integer> getOrderedList() {
        return orderedList;
    }
    private void setOrderedList(List<Integer> orderedList) {
        this.orderedList = orderedList;
    }

    //교재 p.39에 ADL로 기술된 다항식 덧셈 알고리즘을 Java언어로 다음의 add메소드를 통해 구현하였다.
    public static Polynomial add(Polynomial p1, Polynomial p2){
        //인자로 전달된 Polynomial인스턴스의 인스턴스 변수(orderedList)를 반환하는 getOrderedList()메소드를 통해
        //순서리스트가 반환되고 이를 p1Array, p2Array 참조변수가 참조하도록 한다.
        //두 다항식을 더한 것을 저장하기 위해 새로운 배열(ArrayList<E>)을 생성하고 이를 bufList라는 이름의 참조변수가 참조하도록 한다.
        List<Integer> bufList=new ArrayList<>();
        //(ArrayList<E>의 첫번째 원소를 0으로 우선 초기화한다. 이는 r이라는 포인터 변수를 통해서 다항식의 위치를 나타낼 것이기 때문에
        //첫번째 원소를 빈공간으로 둘 수 없어 이와 같은 동작을 취하도록 하였고, 이는 두 다항식의 덧셈 마지막 작업에서 '더해진 다항식의 항수'로 수정해주는 작업을 진행한다.)
        //(실제로 ArrayList<E>의 경우 '인스턴스의 저장 순서를 유지'하는 특성이 있기 때문에 ArrayList에 add()메소드를 통해 저장하는 인스턴스의 순서만을 적절히 해준다면
        //r변수를 통해 다항식의 항 위치를 지정해주지 않아도 되지만 교재의 내용과 방향성을 최대한 일치하기 위해 r변수를 통해 다항식의 항들을 지시하는 포인터 역할을 하도록 하였다.)
        bufList.add(0,0);

        //인자로 전달받은 두 다항식(Polynomial) 인스턴스에서 순서리스트에 해당하는 orederedList필드를 접근하기 위해 getter()메소드를 사용하여주고
        //이 둘을 각각 p1Array,p2Array로 참조한다.
        List<Integer> p1Array=p1.getOrderedList();
        List<Integer> p2Array=p2.getOrderedList();

        //첫번째 원소(인덱스 0번에 해당)를 m,n이라는 변수에 저장한다.(두 다항식의 항수에 해당하는 값)
        int m=p1Array.get(0);
        int n=p2Array.get(0);

        //p,q,r이라는 이름의 변수를 선언과 동시에 2로 초기화한다.
        //더하는 두 다항식을 각각 A다항식, B다항식, 새로운 다항식을 C다항식이라고 할 때,
        //p, q, r는 각각 A,B,C 다항식의 현재 항 위치를 지시하는 포인터 역할을 하는 변수들이다.
        int p=2,q=2,r=2;

        //p가 2*m보다 작거나 같고(and) q가 2*n보다 작거나 같은 동안 다음을 반복한다.
        while((p<=(2*m)) && (q<=(2*n))){
            //다중 if-else문을 통해 지수를 비교한 경우(case)에 따라 나누어 3가지 경우에 따라 동작하도록 한다.
            //만약 각 두 배열의 p,q 인덱스에 해당하는 값이 같은 경우(즉, 두 다항식의 지수가 같은 경우)
            if(p1Array.get(p)==p2Array.get(q)){
                //두 다항식의 계수를 더한다.
                int sum=p1Array.get(p-1)+p2Array.get(q-1);
                //우리가 사용하는 '지수와 계수의 쌍'을 저장하는 방법은 계수가 0이 아닌 항만 저장하여 최악의 메모리 낭비 케이스를 면하기 위한 것이기 때문에
                //두 다항식의 합에 해당하는 계수가 0인 경우를 if문을 통해 검사하여준다.
                //(0이면 저장하지 않고, 0이 아니라면 지수와 계수 쌍을 저장하여 준다.)
                if(sum!=0){
                    //0이 아니라면 r-1, r 번째 인덱스에 각각 계수와 지수 쌍을 저장하고 r을 2증가시킨다.
                    bufList.add(r-1,sum);
                    bufList.add(r,p1Array.get(p));
                    r=r+2;
                }
                //다항식의 계수가 0인지 아닌지와 관계없이 p,q의 값은 2 증가시켜준다.
                p=p+2;
                q=q+2;

            //만약 p2Array가 참조하는 배열의 q 인덱스에 해당하는 값이 큰 경우(B다항식의 지수가 큰 경우)
            }else if(p1Array.get(p)<p2Array.get(q)){
                //새로운 항을 저장한다.
                bufList.add(r-1,p2Array.get(q-1));
                bufList.add(r,p2Array.get(q));
                //q와 r을 2씩 증가시켜 다음 항으로 이동한다.
                q=q+2;
                r=r+2;

            //만약 p1Array가 참조하는 배열의 p 인덱스에 해당하는 값이 큰 경우(A다항식의 지수가 큰 경우)
            }else if(p1Array.get(p)>p2Array.get(q)){
                //새로운 항을 저장한다.
                bufList.add(r-1,p1Array.get(p-1));
                bufList.add(r,p1Array.get(p));
                //p와 r을 2씩 증가시켜 다음 항으로 이동한다.
                p=p+2;
                r=r+2;
            }
        }

        //p가 2*m보다 작거나 같은 동안 다음을 반복한다.(다항식 A의 나머지 항을 새로운 다항식 C에 복사)
        while(p<=(2*m)){
            bufList.add(r-1,p1Array.get(p-1));
            bufList.add(r,p1Array.get(p));
            p=p+2;
            r=r+2;
        }

        //q가 2*m보다 작거나 같은 동안 다음을 반복한다.(다항식 B의 나머지 항을 새로운 다항식 C에 복사)
        while(q<=(2*n)){
            bufList.add(r-1,p2Array.get(q-1));
            bufList.add(r,p2Array.get(q));
            q=q+2;
            r=r+2;
        }


        //bufList의 첫번째 원소(인덱스 0에 해당, 기존에 0을 저장해두었던 인덱스)에 두 다항식의 덧셈 결과에 해당하는 다항식C의 항수를 저장한다.(더행진 다항식의 항수)
        bufList.set(0,(r/2)-1);
        //새로운 Polynomial인스턴스를 인자를 전달받지 않는 생성자를 통해 생성하여주고
        //setOrderedList()메소드의 인자로 bufList를 전달하여 해당 인스턴스의 인스턴스 변수(orderedList)를 초기화하여준다.
        //그리고 이렇게 생성된 Polynomial인스턴스를 return문을 통해 반환하여 주면서 메소드를 종료한다.
        Polynomial result=new Polynomial();
        result.setOrderedList(bufList);
        return result;
    }

    //Polynomail클래스의 인스턴스 변수 orderedList가 참조하는 인스턴스(ArrayList<E>)를 출력하기 위한 메소드
    //(이 때, "x^"와 같은 문자열을 적절히 조합하여 출력하도록 한다.)
    public void printPolynomial(){
        //우선 출력하려는 orderedList에 출력할 항의 지수-계수 쌍이 존재하는지를 if문을 통해 검사한다.
        //만약 orderedList가 항의수(0개)만을 저장하고 있고, 지수-계수 쌍이 존재하지 않는다면 출력할 항이 없기때문에
        //사용자에게 이를 알리고 return문을 통해 메소드호출을 종료한다.
        if(orderedList.size()<=1){
            System.out.println("There are no terms left.");
            return;
        }
        //순서리스트가 저장하고 있는 두번째, 세번째 element를 출력한다.
        //(다항식의 첫번재 항의 지수와 계수에 해당하는 element)
        System.out.print(orderedList.get(1)+"x^");
        System.out.print(orderedList.get(2));
        //orderedList.get(0)를 통해 반환되는 정수(int)는 해당 순서리스트가 저장하고 있는 항수이다.
        //즉, 항의 수-1 번 다음을 반복하여 총 orderedList.get(0)의 값에 해당하는 항의 수를 가지는 다항식이 출력된다.
        for(int i=1;i<orderedList.get(0);i++){
            int num=i*2;
            //음수와 양수에 대해서 if-else문을 통해 서로 다르게 출력할 수 있도록 하였다.
            if(orderedList.get(num+1)<0) {
                System.out.print(orderedList.get(num+1) + "x^");
            }else{
                System.out.print("+"+orderedList.get(num+1)+"x^");
            }
            System.out.print(orderedList.get(num+2));
        }
        System.out.println();
    }
}

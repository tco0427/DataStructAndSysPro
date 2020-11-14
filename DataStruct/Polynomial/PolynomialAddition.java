/*
다항식 덧셈 구현
과목명(분반): 자료구조(1분반)
교수명: 나연묵 교수님
학번: 32180472
학과: 컴퓨터공학과
이름: 김동규
제출일: 2020/09/27
 */

public class PolynomialAddition {
    public static void main(String[] args) {
        //다항식 A를 사용자로 부터 입력받고, 입력받은 문자열을 Polynomial인스턴스의 생성자로 전달하여 Polynomial인스턴스를 생성한다.
        System.out.println("Enter the first polynomial");
        Polynomial polynomialA=new Polynomial(UserInput.getString());
        //다항식 B를 사용자로 부터 입력받고, 입력받은 문자열을 Polynomial인스턴스의 생성자로 전달하여 Polynomial인스턴스를 생성한다.
        System.out.println("Enter the second polynomial");
        Polynomial polynomialB=new Polynomial(UserInput.getString());


        //Polynomail클래스에 정의된 static메소드 add를 클래스 이름을 통해 호출한다.
        //이를 통해 입력받은 두 다항식을 더한 후 그 결과로 반환되는 Polynomial인스턴스를 polynomialC 참조변수로 참조한다.
        Polynomial polynomialC=Polynomial.add(polynomialA,polynomialB);
        System.out.println();

        //printPolynomial 인스턴스 메소드를 통해 결과에 해당하는 다항식을 출력하고 프로그램을 종료한다.
        polynomialC.printPolynomial();
    }
}

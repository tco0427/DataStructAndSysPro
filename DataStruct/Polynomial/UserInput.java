/*
다항식 덧셈 구현
과목명(분반): 자료구조(1분반)
교수명: 나연묵 교수님
학번: 32180472
학과: 컴터공학과
이름: 김동규
제출일: 2020/09/27
 */

import java.util.Scanner;

//사용자로부터 문자열을 입력받기 위한 getString()을 public static하게 제공하는 UserInput클래스를 정의하였다.
public class UserInput {
    //사용자로부터 입력을 받기 위해 Scanner클래스의 생성자에 표준입력스트림인 System.in을 전달하여 인스턴스를 생성하고
    //이를 참조하는 참조변수를 'sc'라는 이름으로 선언한다.
    //private(접근 수준 지시자Access-level Modifiers)로 선언하였으므로 클래스 내부에서만 접근이 가능하다.
    private static Scanner sc=new Scanner(System.in);
    //scan.nextLine()은 공백을 포함하고 '\n'이전까지의 문자열을 입력받고 String인스턴스를 생성하여 받환한다.
    public static String getString(){
        return sc.nextLine();
    }
}

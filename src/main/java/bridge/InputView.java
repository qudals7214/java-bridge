package bridge;

import camp.nextstep.edu.missionutils.Console;

/**
 * 사용자로부터 입력을 받는 역할을 한다.
 */
public class InputView {

    /**
     * 다리의 길이를 입력받는다.
     */
    public int readBridgeSize() {
        int result =0;
        while (result==0){
            try {
                result= Integer.parseInt(Console.readLine());
            }catch (IllegalArgumentException e){
                System.out.println("[ERROR] 다리 길이는 3부터 20 사이의 숫자여야 합니다.");
                System.out.println(e.getMessage());
            }
        }
            return result;
    }

    /**
     * 사용자가 이동할 칸을 입력받는다.
     */
    public String readMoving() {
        String move = Console.readLine();
        while(!move.equals("U") && !move.equals("D")){
            if(move.equals("u")||move.equals("d")){
                move=capsLock(move);
                break;
            }
            System.out.println("[ERROR] U , D 중에서 입력");
            move = Console.readLine();
        }
        return move;
    }

    /**
     * 사용자가 게임을 다시 시도할지 종료할지 여부를 입력받는다.
     */
    public String readGameCommand() {
        String command = Console.readLine();
        while(!command.equals("R") && !command.equals("Q")){
            if(command.equals("r")||command.equals("q")){
                command=capsLock(command);
                break;
            }
            System.out.println("[ERROR] R , Q 중에서 입력");
            command=Console.readLine();
        }
        return command;
    }

    public String capsLock(String capsLock){
        char smallLetter = capsLock.charAt(0);
        smallLetter -=32;
        capsLock= String.valueOf(smallLetter);
        return capsLock;
    }
}

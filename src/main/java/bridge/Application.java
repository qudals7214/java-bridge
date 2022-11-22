package bridge;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        // TODO: 프로그램 구현
        run();
    }

    private static void run(){
        BridgeGame bridgeGame = new BridgeGame();
        int bridgeSize = start();
        List<String>bridgeList = makeBridge(bridgeSize);
        List<String>progress = new ArrayList<>();
        int attempt =1;
        while (progress.size()<bridgeSize){
            bridgeGame.move(progress,move());
            attempt = printMap(bridgeList,progress,attempt);
        }
        end(bridgeList,progress , attempt);
    }
    private static int start(){
        InputView inputView = new InputView();
        System.out.println("다리 건너기 게임을 시작합니다.");
        System.out.println("다리의 길이를 입력해주세요.");
        int bridgeSize = inputView.readBridgeSize();
        while (bridgeSize<3 || bridgeSize>20){
            System.out.println("[ERROR] 다리 길이는 3부터 20 사이의 숫자여야 합니다.");
            bridgeSize = inputView.readBridgeSize();
        }
        System.out.println(bridgeSize);
        return bridgeSize;
    }
    private static List<String> makeBridge(int bridgeSize){
        makeBridgeNumberGenerator makeBridgeNumberGenerator = new makeBridgeNumberGenerator(bridgeSize);
        BridgeMaker bridgeMaker = new BridgeMaker(makeBridgeNumberGenerator);
        List<String>bridgeList = new ArrayList<>(bridgeMaker.makeBridge(bridgeSize));
        return bridgeList;
    }

    static class makeBridgeNumberGenerator implements BridgeNumberGenerator {
        List<Integer> numbers = new ArrayList<>();
        makeBridgeNumberGenerator(int size) {
            for(int i=0; i<size; i++){
                BridgeRandomNumberGenerator bridgeRandomNumberGenerator = new BridgeRandomNumberGenerator();
                numbers.add(bridgeRandomNumberGenerator.generate());
            }
        }
        @Override
        public int generate() {return numbers.remove(0);}
    }
    private static String move(){
        InputView inputView = new InputView();
        System.out.println("이동할 칸을 선택해주세요. (위: U, 아래: D)");
        String move = inputView.readMoving();
        System.out.println(move);
        return move;
    }

    private static int printMap(List<String> bridgeList , List<String> progress , int attempt){
        OutputView outputView = new OutputView();
        outputView.printMap(bridgeList,progress,"U");
        outputView.printMap(bridgeList,progress,"D");
        attempt=checkRetry(bridgeList, progress, attempt);
        return attempt;
    }
    private static int checkRetry(List<String> bridgeList , List<String> progress , int attempt){
        for(int i=0; i<progress.size(); i++){
            if(!progress.get(i).equals(bridgeList.get(i))){
                BridgeGame bridgeGame = new BridgeGame();
                InputView inputView = new InputView();
                System.out.println("게임을 다시 시도할지 여부를 입력해주세요. (재시도: R, 종료: Q)");
                attempt++;
                bridgeGame.retry(inputView.readGameCommand() , attempt , progress);
            }
        }
        return attempt;
    }


    private static void end(List<String>bridgeList , List<String>progress , int attempt){
        String success="성공";
        for(int i=0; i<progress.size(); i++){
            if(!progress.get(i).equals(bridgeList.get(i))) success="실패";
        }
        OutputView outputView = new OutputView();
        outputView.printResult(success,attempt);
    }
}

package discreteMath.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program2 {
	private String fileName;
	private List<List<List<Integer>>> matrixList;

	Program2(String fileName) {
		super();
		this.fileName = fileName;
		this.matrixList = new ArrayList<>();
		this.readFile(this.fileName);
		// 행렬 리스트 확인을 위한 코드
//		for (List<List<Integer>> m : this.matrixList) {
//			this.printMatrix(m);
//			System.out.println();
//		}
	}

	public static void main(String[] args) {
		// 프로그램1 객체 생성
		Program2 p2 = new Program2("input2_2.txt");
		// 다익스트라 알고리즘
		p2.dijkstra();
	}

	void readFile(String fileName) {
		try (Scanner scan = new Scanner(new File(fileName))) {
			while (scan.hasNextLine()) {
				String line = scan.nextLine(); // 파일의 라인 읽어오기
				String[] temp = line.split(" "); // 공백으로 문자열을 나눠 배열에 저장

				// 행렬 생성
				List<List<Integer>> matrix = new ArrayList<>();

				int n = Integer.parseInt(temp[0].trim());
				// 그래프를 넣을 행렬을 n*n의 0으로 채워진 정사각 행렬로 초기화
				for (int j = 0; j < n; j++) {
					matrix.add(new ArrayList<>());
					for (int k = 0; k < n; k++)
						matrix.get(j).add(0);
				}

				// n번 만큼 파일의 다음줄을 읽어 행렬의 각 행을 설정
				for (int i = 0; i < n; i++) {
					line = scan.nextLine();
					String[] temp2 = line.split(" ");

					for (int k = 1; k < temp2.length; k += 2)
						matrix.get(i).set(Integer.parseInt(temp2[k].trim()) - 1, Integer.parseInt(temp2[k + 1].trim()));
				}
				this.matrixList.add(matrix); // 행렬 리스트에 행렬 추가
			}
		} catch (FileNotFoundException e) {
			System.out.println("파일을 읽어올 수 없습니다. \n파일명을 확인하세요.");
		}
	}

	// 다익스트라 알고리즘
	void dijkstra() {
		// 행렬 리스트의 각 행렬에대해 다익스트라 알고리즘 적용
		int num = 0;
		for (List<List<Integer>> m : this.matrixList) {
			num++; // 몇 번째 행렬(그래프)인지 count
			// 코드 구현

			// 거리 리스트 생성, 초기화
			List<Integer> D = new ArrayList<>();
			D.addAll(m.get(0));
			// D 확인 코드
//			System.out.print("D = ");
//			for(Integer a : D) { 
//				System.out.print(a+" ");
//			}System.out.println();

			// Selected 정점 리스트 생성, 초기화
			List<Integer> S = new ArrayList<>();
			S.add(0);

			// Way 경로 저장 리스트 생성, 초기화
			ArrayList<Integer>[] Way = new ArrayList[D.size()];

			for (int i = 0; i < D.size(); i++) {
				Way[i] = new ArrayList<Integer>();
				Way[i].add(0);
			}

			// Way 리스트 확인 코드
//			System.out.println("Way = ");
//			for(int i=0; i<D.size(); i++) {
//				for(int j=0; j<Way[i].size(); j++) {
//					System.out.print(Way[i].get(j)+" ");
//				}System.out.println();
//			}

			while (S.size() < D.size()) { // 모든 정점이 선택될 때까지

				// w 찾기
				int w = 1;
				int least = Integer.MAX_VALUE;
				for (int i = 1; i < D.size(); i++) {
					if (S.contains(i))
						continue; // i정점이 S에 있으면 skip
					if (D.get(i) == 0)
						continue; // 무한대를 0으로 저장했기때문에 0이면 skip
					if (least > D.get(i)) {
						least = D.get(i);
						w = i;
					}
				}
				S.add(w);
//			System.out.println("****** w = "+w+" ******");

				// D업데이트
				for (int i = 1; i < D.size(); i++) {
					if (S.contains(i)) { // 이미 i정점이 S에 있으면 건너뜀
//					System.out.println(i+" : 이미 선택완료된 정점");
						continue;
					}
					if (m.get(w).get(i) == 0) { // 거리가 무한대면 건너뜀
//					System.out.println("거리가 무한대");
						continue;
					}

					int oriD = D.get(i); // 원래 D값 저장.
					if (D.get(i) == 0)
						oriD = Integer.MAX_VALUE; // 0이면 oriD에 무한대 저장.
					D.set(i, Math.min(oriD, D.get(w) + m.get(w).get(i)));
//				System.out.println("D["+i+"] = "+D.get(i));

					if (oriD > D.get(i)) { // 원래 D값이랑 다르면 Way저장
						ArrayList<Integer> tmp = new ArrayList<>(); // 임시 경로 생성
						tmp.add(0); // 시작점 추가
						if (Way[w].size() > 1) { // w로 가는 경로가 시작점->?->w이라면
							for (int j = 1; j < Way[w].size(); j++) {
								tmp.add(Way[w].get(j)); // 임시경로에 시작점~w경로 추가
							}

						}
						tmp.add(w); // 끝에 w까지 경로에 추가
						Way[i] = tmp; // 최종 경로 저장
					}

					// Way 확인 코드
//			System.out.println();			
//			System.out.println("Way = ");
//			for(int x=0; x<D.size(); x++) {
//				for(int y=0; y<Way[x].size(); y++) {
//					System.out.print(Way[x].get(y)+" ");
//				}System.out.println();
//			}

					// D 확인
//			System.out.print("D = ");
//			for(Integer a : D) { 
//				System.out.print(a+" ");
//			}System.out.println();

				}
			}
			// 최종결과 출력
			System.out.println("그래프 [" + num + "]");
			System.out.println("------------------------------");
			System.out.println("시작점 : 1");
			for (int i = 1; i < D.size(); i++) {
				System.out.print("정점 [" + (i + 1) + "]:");
				for (int j = 0; j < Way[i].size(); j++) {
					System.out.print((Way[i].get(j) + 1) + " - ");
				}
				System.out.println((i + 1) + ", 길이: " + D.get(i));
			}
			System.out.println("==============================");
		}

	}

	// 행렬 확인을 위한 프린트 함수
	void printMatrix(List<List<Integer>> matrix) {
		for (List<Integer> list : matrix) {
			for (Integer d : list) {
				System.out.print(d + " ");
			}
			System.out.println();
		}
	}
}

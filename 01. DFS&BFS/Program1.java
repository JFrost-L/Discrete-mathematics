package discreteMath.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Program1 {
	private String fileName;
	private static List<List<List<Integer>>> matrixList;
	private static boolean[] visited = new boolean[1024];

	Program1(String fileName) {
		super();
		this.fileName = fileName;
		matrixList = new ArrayList<>();
		this.readFile(this.fileName);
		// 행렬 리스트 확인을 위한 코드
//		for (List<List<Integer>> m : matrixList) {
//			this.printMatrix(m);
//			System.out.println();
//		}
	}

	public static void main(String[] args) {
		// 프로그램1 객체 생성
		Program1 p1 = new Program1("input1_3.txt");
		// 너비 우선 탐색
		for (int i = 0; i < matrixList.size(); i++) {
			System.out.println("그래프  [" + (i + 1) + "]");
			System.out.println("----------------------------");
			for (int j = 0; j < 1024; j++) {
				visited[j] = false;
			}
			System.out.println("--- 깊이 우선 탐색 ---");
			p1.dfs(i, 0);
			System.out.println();
			p1.bfs(i);
			System.out.println("\n");
		}
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

					for (int k = 1; k < temp2.length; k++)
						matrix.get(i).set(Integer.parseInt(temp2[k].trim()) - 1, 1);
				}
				matrixList.add(matrix); // 행렬 리스트에 행렬 추가
			}
		} catch (FileNotFoundException e) {
			System.out.println("파일을 읽어올 수 없습니다. \n파일명을 확인하세요.");
		}
	}

	// 깊이 우선 탐색
	void dfs(int start, int node) { // 해당 그래프 : start
		// 행렬 리스트의 각 행렬에 대하여 깊이 우선 탐색
		int length = matrixList.get(start).get(node).size();
		if (node != 0) {
			System.out.print(" - ");
		}
		System.out.print((node + 1));
		visited[node] = true;
		for (int i = 0; i < length; i++) {
			if (!visited[i] && matrixList.get(start).get(node).get(i) == 1) {
				dfs(start, i);
			}
		}
	}

	// 너비 우선 탐색
	void bfs(int start) {// 해당 그래프 : start
		// 행렬 리스트의 각 행렬에 대하여 너비 우선 탐색
		boolean[] bfsVisited = new boolean[matrixList.get(start).size()];
		for (int i = 0; i < matrixList.get(start).size(); i++) {
			bfsVisited[i] = false;
		}
		Queue<Integer> que = new LinkedList<>();
		System.out.println("--- 너비 우선 탐색 ---");

		que.add(1);
		bfsVisited[0] = true;

		while (!que.isEmpty()) {
			int v = que.poll();
			for (int i = 1; i < bfsVisited.length; i++) {
				if (matrixList.get(start).get(v - 1).get(i) == 1 && !bfsVisited[i]) {
					que.add(i + 1);
					bfsVisited[i] = true;
				}
			}
			if (que.isEmpty()) {
				System.out.print(v);
			} else {
				System.out.print(v + " - ");
			}
		}
		System.out.println();
		System.out.println("=============================");
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

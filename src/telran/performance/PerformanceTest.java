package telran.performance;

public abstract class PerformanceTest {
	private String testName;
	private int nRuns;

	public PerformanceTest(String testName, int nRuns) {
		super();
		this.testName = testName;
		this.nRuns = nRuns;
	}

	abstract protected void runTest();

	public void run() {
		Long startTest = System.currentTimeMillis();
		for (int i = 0; i < nRuns; i++) {
			runTest();
		}
		Long finishTest = System.currentTimeMillis();
		infoTest(startTest, finishTest);
	}

	private void infoTest(Long start, Long finish) {
		System.out.printf("Test %s; Number of the runs: %d; Running time: %dMs\n", testName, nRuns, finish - start);
	}

}

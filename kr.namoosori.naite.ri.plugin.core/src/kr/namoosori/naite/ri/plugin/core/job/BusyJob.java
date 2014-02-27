package kr.namoosori.naite.ri.plugin.core.job;

/**
 * Runnable 인터페이스를 구현한 추상클래스
 * 쓰레드가 종료 후 데이터를 리턴해 줄 필요가 있을 시 data에 세팅한다.
 * 받는 쪽에서 알아서 형변환하여 사용
 * 
 * @author syhan
 */
public abstract class BusyJob implements Runnable {
	//
	private Object data;

    private Object[] arrayData;

    @Override
    public abstract void run();

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object[] getArrayData() {
        return arrayData;
    }

    public void setArrayData(Object[] arrayData) {
        this.arrayData = arrayData;
    }
}

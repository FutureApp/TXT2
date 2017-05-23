package ue1.similarity.analyse;

public class SSDTupele {

	String k1, k2;
	Double k3;

	public SSDTupele(String k1, String k2, Double k3) {
		super();
		this.k1 = k1;
		this.k2 = k2;
		this.k3 = k3;
	}

	public String getK1() {
		return k1;
	}

	public void setK1(String k1) {
		this.k1 = k1;
	}

	public String getK2() {
		return k2;
	}

	public void setK2(String k2) {
		this.k2 = k2;
	}

	public Double getK3() {
		return k3;
	}

	public void setK3(Double k3) {
		this.k3 = k3;
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("(");
		build.append(k1 + ",");
		build.append(k2 + ",");
		build.append(k3 + ")");
		return build.toString();
	}
	public String toStringForList() {
		StringBuilder build = new StringBuilder();
		build.append(k1 + "|");
		build.append(k2 + "|");
		build.append(k3 + "");
		return build.toString();
	}
}

package valable.model;

public class ValaLocalVariable extends ValaEntity {

	private String type;

	public ValaLocalVariable(String name) {
		super(name);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public <R> R accept(ValaEntityVisitor<R> visitor) {
		return visitor.visitLocalVariable(this);
	}

}

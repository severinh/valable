package valable.model;

public interface ValaEntityVisitor<R> {

	public R visitEntity(ValaEntity entity);

	public R visitField(ValaField field);

	public R visitMethod(ValaMethod method);

	public R visitType(ValaType type);

	public R visitLocalVariable(ValaLocalVariable localVariable);

}

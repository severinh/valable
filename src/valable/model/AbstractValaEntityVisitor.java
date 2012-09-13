package valable.model;

public class AbstractValaEntityVisitor<R> implements ValaEntityVisitor<R> {

	@Override
	public R visitEntity(ValaEntity entity) {
		return null;
	}

	@Override
	public R visitField(ValaField field) {
		return visitEntity(field);
	}

	@Override
	public R visitMethod(ValaMethod method) {
		return visitEntity(method);
	}

	@Override
	public R visitType(ValaType type) {
		return visitEntity(type);
	}

	@Override
	public R visitLocalVariable(ValaLocalVariable localVariable) {
		return visitEntity(localVariable);
	}

}

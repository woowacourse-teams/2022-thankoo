export default class CustomError extends Error {
  name: string;
  errorHandler: ErrorHandler;

  constructor(message?: string, errorHandler?: ErrorHandler) {
    super(message);
    this.name = new.target.name;
    // new 연산자로 호출된 생성자의 이름
    this.errorHandler = errorHandler;
  }

  executeErrorHandler() {
    if (this.errorHandler) {
      this.errorHandler(this);
      // 에러 핸들러는 핸들링 대상 에러를 핸들러로 받음.
    }
  }
}

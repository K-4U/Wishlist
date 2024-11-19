export class LoginError extends Error {
  code: number;

  constructor(message: string, code: number) {
    super(message);
    this.name = 'LoginError';
    this.code = code;
  }
}

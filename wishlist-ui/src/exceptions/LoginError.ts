export class LoginError extends Error {
  code: number;
  error: string;

  constructor(message: string, code: number) {
    super(message);
    this.name = 'LoginError';
    this.code = code;
  }
}

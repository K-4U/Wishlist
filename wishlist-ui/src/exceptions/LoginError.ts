export class LoginError extends Error {
  code: number;
  error: string;

  constructor(message: string, code: number, error: string) {
    super(message);
    this.name = 'LoginError';
    this.code = code;
    this.error = error;
  }
}

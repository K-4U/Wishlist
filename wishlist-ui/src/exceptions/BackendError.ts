export class BackendError extends Error {
  code: number;
  error: string;

  constructor(message: string, code: number) {
    super(message);
    this.name = 'BackendError';
    this.code = code;
  }
}

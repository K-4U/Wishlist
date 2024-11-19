export class BackendError extends Error {
  code: number;

  constructor(message: string, code: number) {
    super(message);
    this.name = 'BackendError';
    this.code = code;
  }
}

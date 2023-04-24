export class Alert {
    type: AlertType;
    message: string;

    constructor(message: string, type: AlertType) {
        this.message = message;
        this.type = type;
    }
}

export enum AlertType {
    Success,
    Error
}
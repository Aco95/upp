import { Injectable } from "@angular/core";

@Injectable()
export class CurrentUserHolderService {
  currentUserEmail = "None";

  constructor() {}

  setCurrentUser(email: string) {
    this.currentUserEmail = email;
  }

  getCurrentUser(): string {
    return this.currentUserEmail;
  }
}

import { CurrentUserHolderService } from "./../../services/current-user-holder.service";
import { Component, OnInit } from "@angular/core";
import { AuthService } from "../auth/auth.service";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.css"]
})
export class HeaderComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private currentUserHolderService: CurrentUserHolderService
  ) {}

  ngOnInit() {}

  onLogout() {
    this.currentUserHolderService.setCurrentUser("None");
    this.authService.logout();
  }
}

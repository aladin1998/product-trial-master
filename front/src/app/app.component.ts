import {
  Component,
  computed,
  inject,
} from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { BadgeModule } from 'primeng/badge';
import { ProductsService } from "./products/data-access/products.service";
import { ToastModule } from 'primeng/toast';

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, BadgeModule, ToastModule],
})
export class AppComponent {

  title = "ALTEN SHOP";

  private readonly productsService = inject(ProductsService);

  private router = inject(Router);
    

  productsInCartCount = computed(() => this.productsService.productsInCart().length );


  openCart() {
    this.router.navigate(['/products/cart']);
  }

}
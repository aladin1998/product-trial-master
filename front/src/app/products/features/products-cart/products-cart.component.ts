import { CurrencyPipe } from "@angular/common";
import { Component, OnInit, inject, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductItemComponent } from "app/shared/features/product-item/product-item.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { RatingModule } from 'primeng/rating';

@Component({
  selector: "app-products-cart",
  templateUrl: "./products-cart.component.html",
  styleUrls: ["./products-cart.component.scss"],
  standalone: true,
    imports: [ProductItemComponent, RatingModule, DataViewModule, CardModule, ButtonModule, DialogModule, FormsModule]
  })
export class ProductsCartComponent implements OnInit {


  private readonly productsService = inject(ProductsService);


  public readonly productsInCart = this.productsService.productsInCart;

  ngOnInit(): void {

  }

  onDelete(product: Product) {
    this.productsService.deleteProductFromCart(product.id);
  }
  
}

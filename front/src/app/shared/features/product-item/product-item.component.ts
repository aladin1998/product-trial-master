import { CommonModule } from "@angular/common";
import { Component, inject, Input, OnInit } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { InventoryStatus } from "app/shared/enums/inventory-status.enum";
import { Severity } from "app/shared/enums/severity.enum";
import { ButtonModule } from "primeng/button";
import { TagModule } from "primeng/tag";
import { CascadeSelectModule } from 'primeng/cascadeselect';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule } from "@angular/forms";

@Component({
  selector: "product-item",
  templateUrl: "./product-item.component.html",
  styleUrls: ["./product-item.component.scss"],
  standalone: true,
  imports: [ButtonModule, CommonModule, TagModule, CascadeSelectModule, InputNumberModule, FormsModule],
})
export class ProductItemComponent implements OnInit {


  private readonly productsService = inject(ProductsService);
  

  @Input() product: Product | undefined ;

  first: number = 0;

  quantity: number =  1;

  ngOnInit(): void {
    if(this.product !== undefined && this.product.quantity !== undefined) {
      this.quantity = this.product.quantity;
    }
  }


  getSeverity(product?: Product) {
    switch (product?.inventoryStatus) {
        case InventoryStatus.InStock:
            return Severity.Success;

        case InventoryStatus.LowStock:
            return Severity.Warning;

        case InventoryStatus.OutOfStock:
            return Severity.Danger;

        default:
          return undefined;
    }
  }

  public onAddToCart(product: Product | undefined) {
    if(product !== undefined)
    this.productsService.addProductIntoCart({...product, isInCart: true, quantity: this.quantity});

  }

  public onDeleteFromCart(product: Product | undefined) {

    if (product?.id !== undefined) {
      this.productsService.deleteProductFromCart(product.id);
    }

  }

}

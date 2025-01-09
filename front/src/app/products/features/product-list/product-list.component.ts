import { Component, OnInit, inject } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { DataViewModule } from 'primeng/dataview';
import { InputTextModule } from 'primeng/inputtext';
import { ProductItemComponent } from "app/shared/features/product-item/product-item.component";
import { ButtonModule } from "primeng/button";

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [ProductItemComponent, InputTextModule, DataViewModule, FormsModule, ButtonModule ]
})
export class ProductListComponent implements OnInit {

  private readonly productsService = inject(ProductsService);

  public products = this.productsService.products;

  public filteredProducts: Product[] = this.products();

  filterText: string = ''; 
  first: number = 0; 
  itemsPerPage: number = 5;

  public isDialogVisible = false;
  public isCreation = false;

  ngOnInit() {
    this.productsService.get().subscribe(
      products => this.filteredProducts = products
    )
  }

  onFilter() {
    this.filteredProducts = this.filteredProducts.filter(product => 
      product.name.toLowerCase().includes(this.filterText.toLowerCase()) ||
      product.category.toLowerCase().includes(this.filterText.toLowerCase())
    );
    this.first = 0;
  }
  
  onPage(event: { first: number; rows: number; }) {
    this.first = event.first;
    this.itemsPerPage = event.rows;
  }

  onClearFilter() {
    this.filteredProducts = this.products();
    this.filterText = '';
    this.first = 0;
  }

}
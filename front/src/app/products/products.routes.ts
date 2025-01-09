import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { ProductListComponent } from "./features/product-list/product-list.component";
import { ProductsCartComponent } from "./features/products-cart/products-cart.component";

export const PRODUCTS_ROUTES: Routes = [
	{
		path: "list",
		component: ProductListComponent,
	},
	{
		path: "cart",
		component: ProductsCartComponent,
	},
	{ path: "**", redirectTo: "list" },
];

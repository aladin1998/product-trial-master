import { Injectable, inject, signal } from "@angular/core";
import { Product } from "./product.model";
import { HttpClient } from "@angular/common/http";
import { catchError, map, Observable, tap } from "rxjs";

@Injectable({
    providedIn: "root"
}) export class ProductsService {

    private readonly http = inject(HttpClient);
    private readonly path = "/api/products";
    
    private readonly _products = signal<Product[]>([]);

    public readonly products = this._products.asReadonly();

    private readonly _productsInCart = signal<Product[]>([]);

    public readonly productsInCart = this._productsInCart.asReadonly();

    public get(): Observable<Product[]> {
        return this.http.get<Product[]>(this.path).pipe(
            map((products) => 
                products.map((product) => ({
                    ...product, 
                    quantity: 0
                }))
            ),
            catchError((error) => {
                return this.http.get<Product[]>("assets/products.json");
            }),
            tap((products) => this._products.set(products)),
        );
    }

    public addProductIntoCart(product: Product) {
        const productsInCartIds = this._productsInCart().map(product => product.id);

        if (!productsInCartIds.includes(product.id)) {
            this._productsInCart.update(products => [product, ...products]);
        }
    }

    public deleteProductFromCart(productId: number) {

        this._productsInCart.update(products => products.filter(product => product.id !== productId))
    }
}
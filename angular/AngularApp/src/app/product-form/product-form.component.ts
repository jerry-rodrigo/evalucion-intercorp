import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { ProductRequest, ProductResponse } from '../models/product.model';
import { catchError } from 'rxjs/operators'; // Importar catchError
import { of } from 'rxjs'; // Importar of
import { HttpErrorResponse } from '@angular/common/http'; // Importar HttpErrorResponse

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css'],
})
export class ProductFormComponent implements OnInit {
  products: ProductResponse[] = [];
  product: ProductRequest = {
    sku: '',
    name: '',
    description: '',
    price: 0,
    status: true,
  };
  isEditMode = false;
  selectedProductId: string | null = null;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe((data) => {
      this.products = data;
    });
  }

  onSubmit(): void {
    if (this.isEditMode) {
        if (this.selectedProductId) {
            this.productService.updateProduct(this.selectedProductId, this.product).pipe(
                catchError((error: any) => {
                    console.error('Error al actualizar el producto:', error);
                    alert('Error al actualizar el producto: ' + error.message);
                    return of(null); 
                })
            ).subscribe(() => {
                this.loadProducts();
                this.resetForm();
            });
        }
    } else {
        this.productService.addProduct(this.product).pipe(
            catchError((error: any) => {
                console.error('Error al agregar el producto:', error);
                alert('Error al agregar el producto: ' + error.message);
                return of(null);
            })
        ).subscribe(() => {
            this.loadProducts();
            this.resetForm();
        });
    }
}

  editProduct(product: ProductResponse): void {
    this.product = { ...product };
    this.isEditMode = true;
    this.selectedProductId = product.id;
  }

  resetForm(): void {
    this.product = {
      sku: '',
      name: '',
      description: '',
      price: 0,
      status: true,
    };
    this.isEditMode = false;
    this.selectedProductId = null;
  }
}

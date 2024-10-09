export interface ProductRequest {
    sku: string;
    name: string;
    description: string;
    price: number;
    status: boolean;
  }
  
  export interface ProductResponse extends ProductRequest {
    id: string;
  }
  
import { Component, inject } from "@angular/core";
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { MessageService } from "primeng/api";
import { ButtonModule } from "primeng/button";
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';

@Component({
  selector: "app-contact",
  templateUrl: "./contact.component.html",
  styleUrls: ["./contact.component.scss"],
  standalone: true,
  imports: [InputTextareaModule, InputTextModule, ButtonModule, FormsModule, ReactiveFormsModule]
  })
export class ContactComponent {


  private fb =  inject(FormBuilder);
  
  private messageService =  inject(MessageService);

  contactForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    message: ['', [Validators.required, Validators.maxLength(300)]]
  });

  onSubmit() {
    if (this.contactForm.valid) {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Demande de contact envoyée avec succès' });
    }
  }  
}

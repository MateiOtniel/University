namespace TestSGBD{
    partial class MainForm{
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing){
            if(disposing && (components != null)){
                components.Dispose();
            }

            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent(){
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.titluTextBox = new System.Windows.Forms.TextBox();
            this.descriereTextBox = new System.Windows.Forms.TextBox();
            this.dataDepuneriiTextBox = new System.Windows.Forms.TextBox();
            this.bugetTextBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.modificaTitluTextBox = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(12, 12);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowTemplate.Height = 24;
            this.dataGridView1.Size = new System.Drawing.Size(583, 290);
            this.dataGridView1.TabIndex = 0;
            // 
            // dataGridView2
            // 
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Location = new System.Drawing.Point(12, 326);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.RowTemplate.Height = 24;
            this.dataGridView2.Size = new System.Drawing.Size(583, 307);
            this.dataGridView2.TabIndex = 1;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(691, 202);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 2;
            this.button1.Text = "Adauga";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(691, 398);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 3;
            this.button2.Text = "Modifica";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(691, 553);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(75, 23);
            this.button3.TabIndex = 4;
            this.button3.Text = "Sterge";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // titluTextBox
            // 
            this.titluTextBox.Location = new System.Drawing.Point(700, 22);
            this.titluTextBox.Name = "titluTextBox";
            this.titluTextBox.Size = new System.Drawing.Size(100, 22);
            this.titluTextBox.TabIndex = 5;
            // 
            // descriereTextBox
            // 
            this.descriereTextBox.Location = new System.Drawing.Point(700, 50);
            this.descriereTextBox.Name = "descriereTextBox";
            this.descriereTextBox.Size = new System.Drawing.Size(100, 22);
            this.descriereTextBox.TabIndex = 6;
            // 
            // dataDepuneriiTextBox
            // 
            this.dataDepuneriiTextBox.Location = new System.Drawing.Point(700, 78);
            this.dataDepuneriiTextBox.Name = "dataDepuneriiTextBox";
            this.dataDepuneriiTextBox.Size = new System.Drawing.Size(100, 22);
            this.dataDepuneriiTextBox.TabIndex = 7;
            // 
            // bugetTextBox
            // 
            this.bugetTextBox.Location = new System.Drawing.Point(700, 106);
            this.bugetTextBox.Name = "bugetTextBox";
            this.bugetTextBox.Size = new System.Drawing.Size(100, 22);
            this.bugetTextBox.TabIndex = 8;
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(601, 25);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(100, 23);
            this.label1.TabIndex = 10;
            this.label1.Text = "Titlu";
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(601, 49);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(100, 23);
            this.label2.TabIndex = 11;
            this.label2.Text = "Descriere";
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(601, 75);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(100, 23);
            this.label3.TabIndex = 12;
            this.label3.Text = "Data Depunerii";
            // 
            // label4
            // 
            this.label4.Location = new System.Drawing.Point(601, 106);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(100, 23);
            this.label4.TabIndex = 13;
            this.label4.Text = "Buget";
            // 
            // label5
            // 
            this.label5.Location = new System.Drawing.Point(610, 368);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(100, 23);
            this.label5.TabIndex = 14;
            this.label5.Text = "Titlu nou";
            // 
            // modificaTitluTextBox
            // 
            this.modificaTitluTextBox.Location = new System.Drawing.Point(691, 365);
            this.modificaTitluTextBox.Name = "modificaTitluTextBox";
            this.modificaTitluTextBox.Size = new System.Drawing.Size(100, 22);
            this.modificaTitluTextBox.TabIndex = 15;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(863, 725);
            this.Controls.Add(this.modificaTitluTextBox);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.bugetTextBox);
            this.Controls.Add(this.dataDepuneriiTextBox);
            this.Controls.Add(this.descriereTextBox);
            this.Controls.Add(this.titluTextBox);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.dataGridView1);
            this.Name = "MainForm";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox modificaTitluTextBox;

        private System.Windows.Forms.Label label4;

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;

        private System.Windows.Forms.TextBox descriereTextBox;
        private System.Windows.Forms.TextBox dataDepuneriiTextBox;
        private System.Windows.Forms.TextBox bugetTextBox;

        private System.Windows.Forms.TextBox titluTextBox;

        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button3;

        private System.Windows.Forms.Button button1;

        private System.Windows.Forms.DataGridView dataGridView2;

        private System.Windows.Forms.DataGridView dataGridView1;

        #endregion
    }
}
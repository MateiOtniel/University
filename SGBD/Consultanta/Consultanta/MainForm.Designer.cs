namespace Consultanta{
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
            this.addButton = new System.Windows.Forms.Button();
            this.updateButton = new System.Windows.Forms.Button();
            this.deleteButton = new System.Windows.Forms.Button();
            this.addPanel = new System.Windows.Forms.Panel();
            this.addValueButton = new System.Windows.Forms.Button();
            this.addLabel = new System.Windows.Forms.Label();
            this.tipMeetingTextBox = new System.Windows.Forms.TextBox();
            this.updateValueButton = new System.Windows.Forms.Button();
            this.updatePanel = new System.Windows.Forms.Panel();
            this.updateLabel = new System.Windows.Forms.Label();
            this.tipMeetingTextBox2 = new System.Windows.Forms.TextBox();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.addPanel.SuspendLayout();
            this.updatePanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // dataGridView1
            // 
            this.dataGridView1.AllowUserToAddRows = false;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(27, 31);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowTemplate.Height = 24;
            this.dataGridView1.Size = new System.Drawing.Size(393, 371);
            this.dataGridView1.TabIndex = 0;
            // 
            // dataGridView2
            // 
            this.dataGridView2.AllowUserToAddRows = false;
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Location = new System.Drawing.Point(482, 31);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.RowTemplate.Height = 24;
            this.dataGridView2.Size = new System.Drawing.Size(573, 371);
            this.dataGridView2.TabIndex = 1;
            // 
            // addButton
            // 
            this.addButton.Location = new System.Drawing.Point(180, 432);
            this.addButton.Name = "addButton";
            this.addButton.Size = new System.Drawing.Size(84, 29);
            this.addButton.TabIndex = 2;
            this.addButton.Text = "Add Client";
            this.addButton.UseVisualStyleBackColor = true;
            this.addButton.Click += new System.EventHandler(this.AddAction);
            // 
            // updateButton
            // 
            this.updateButton.Location = new System.Drawing.Point(614, 432);
            this.updateButton.Name = "updateButton";
            this.updateButton.Size = new System.Drawing.Size(84, 29);
            this.updateButton.TabIndex = 3;
            this.updateButton.Text = "Update";
            this.updateButton.UseVisualStyleBackColor = true;
            this.updateButton.Click += new System.EventHandler(this.UpdateAction);
            // 
            // deleteButton
            // 
            this.deleteButton.Location = new System.Drawing.Point(879, 432);
            this.deleteButton.Name = "deleteButton";
            this.deleteButton.Size = new System.Drawing.Size(84, 29);
            this.deleteButton.TabIndex = 4;
            this.deleteButton.Text = "Delete";
            this.deleteButton.UseVisualStyleBackColor = true;
            this.deleteButton.Click += new System.EventHandler(this.DeleteAction);
            // 
            // addPanel
            // 
            this.addPanel.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.addPanel.Controls.Add(this.addValueButton);
            this.addPanel.Controls.Add(this.addLabel);
            this.addPanel.Controls.Add(this.tipMeetingTextBox);
            this.addPanel.Location = new System.Drawing.Point(87, 500);
            this.addPanel.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.addPanel.Name = "addPanel";
            this.addPanel.Size = new System.Drawing.Size(291, 129);
            this.addPanel.TabIndex = 5;
            // 
            // addValueButton
            // 
            this.addValueButton.Location = new System.Drawing.Point(74, 68);
            this.addValueButton.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.addValueButton.Name = "addValueButton";
            this.addValueButton.Size = new System.Drawing.Size(134, 35);
            this.addValueButton.TabIndex = 6;
            this.addValueButton.Text = "Add Meeting";
            this.addValueButton.UseVisualStyleBackColor = true;
            this.addValueButton.Click += new System.EventHandler(this.AddMeetingAction);
            // 
            // addLabel
            // 
            this.addLabel.Location = new System.Drawing.Point(4, 29);
            this.addLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.addLabel.Name = "addLabel";
            this.addLabel.Size = new System.Drawing.Size(105, 31);
            this.addLabel.TabIndex = 3;
            this.addLabel.Text = "Tip Meeting";
            this.addLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // tipMeetingTextBox
            // 
            this.tipMeetingTextBox.AccessibleDescription = "";
            this.tipMeetingTextBox.Location = new System.Drawing.Point(117, 29);
            this.tipMeetingTextBox.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.tipMeetingTextBox.Name = "tipMeetingTextBox";
            this.tipMeetingTextBox.Size = new System.Drawing.Size(168, 26);
            this.tipMeetingTextBox.TabIndex = 0;
            this.tipMeetingTextBox.Text = "\r\n";
            this.tipMeetingTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // updateValueButton
            // 
            this.updateValueButton.Location = new System.Drawing.Point(63, 69);
            this.updateValueButton.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.updateValueButton.Name = "updateValueButton";
            this.updateValueButton.Size = new System.Drawing.Size(170, 35);
            this.updateValueButton.TabIndex = 6;
            this.updateValueButton.Text = "Update Meeting\r\n";
            this.updateValueButton.UseVisualStyleBackColor = true;
            this.updateValueButton.Click += new System.EventHandler(this.UpdateMeetingAction);
            // 
            // updatePanel
            // 
            this.updatePanel.BackColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.updatePanel.Controls.Add(this.updateValueButton);
            this.updatePanel.Controls.Add(this.updateLabel);
            this.updatePanel.Controls.Add(this.tipMeetingTextBox2);
            this.updatePanel.Location = new System.Drawing.Point(510, 500);
            this.updatePanel.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.updatePanel.Name = "updatePanel";
            this.updatePanel.Size = new System.Drawing.Size(291, 129);
            this.updatePanel.TabIndex = 7;
            // 
            // updateLabel
            // 
            this.updateLabel.Location = new System.Drawing.Point(4, 29);
            this.updateLabel.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.updateLabel.Name = "updateLabel";
            this.updateLabel.Size = new System.Drawing.Size(105, 31);
            this.updateLabel.TabIndex = 3;
            this.updateLabel.Text = "Tip Meeting";
            this.updateLabel.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // tipMeetingTextBox2
            // 
            this.tipMeetingTextBox2.AccessibleDescription = "";
            this.tipMeetingTextBox2.Location = new System.Drawing.Point(117, 29);
            this.tipMeetingTextBox2.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.tipMeetingTextBox2.Name = "tipMeetingTextBox2";
            this.tipMeetingTextBox2.Size = new System.Drawing.Size(168, 26);
            this.tipMeetingTextBox2.TabIndex = 0;
            this.tipMeetingTextBox2.Text = "\r\n";
            this.tipMeetingTextBox2.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1068, 743);
            this.Controls.Add(this.updatePanel);
            this.Controls.Add(this.addPanel);
            this.Controls.Add(this.deleteButton);
            this.Controls.Add(this.updateButton);
            this.Controls.Add(this.addButton);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.dataGridView1);
            this.Name = "MainForm";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.addPanel.ResumeLayout(false);
            this.addPanel.PerformLayout();
            this.updatePanel.ResumeLayout(false);
            this.updatePanel.PerformLayout();
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.Button updateValueButton;
        private System.Windows.Forms.Panel updatePanel;
        private System.Windows.Forms.Label updateLabel;
        private System.Windows.Forms.TextBox tipMeetingTextBox2;

        private System.Windows.Forms.Button addValueButton;

        private System.Windows.Forms.Label addLabel;

        private System.Windows.Forms.TextBox tipMeetingTextBox;

        private System.Windows.Forms.Panel addPanel;

        private System.Windows.Forms.Button updateButton;
        private System.Windows.Forms.Button deleteButton;

        private System.Windows.Forms.Button addButton;

        private System.Windows.Forms.DataGridView dataGridView2;

        private System.Windows.Forms.DataGridView dataGridView1;

        #endregion
    }
}
using System.ComponentModel;

namespace client{
    partial class MainForm{
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private IContainer components = null;

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
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.Home = new System.Windows.Forms.TabPage();
            this.Home.Name = "Home";
            this.addResultPanel = new System.Windows.Forms.Panel();
            this.button1 = new System.Windows.Forms.Button();
            this.dateTimePicker = new System.Windows.Forms.DateTimePicker();
            this.label2 = new System.Windows.Forms.Label();
            this.pointsTextBox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.addButton = new System.Windows.Forms.Button();
            this.participantsDataGridView = new System.Windows.Forms.DataGridView();
            this.selectedRefereeLabel = new System.Windows.Forms.Label();
            this.Raport = new System.Windows.Forms.TabPage();
            this.Raport.Name = "Raport";
            this.label3 = new System.Windows.Forms.Label();
            this.raportDataGridView = new System.Windows.Forms.DataGridView();
            this.button2 = new System.Windows.Forms.Button();
            this.tabControl1.SuspendLayout();
            this.Home.SuspendLayout();
            this.addResultPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.participantsDataGridView)).BeginInit();
            this.Raport.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.raportDataGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.Home);
            this.tabControl1.Controls.Add(this.Raport);
            this.tabControl1.Location = new System.Drawing.Point(12, 12);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(800, 530);
            this.tabControl1.TabIndex = 0;
            // 
            // tabPage1
            // 
            this.Home.Controls.Add(this.addResultPanel);
            this.Home.Controls.Add(this.addButton);
            this.Home.Controls.Add(this.participantsDataGridView);
            this.Home.Controls.Add(this.selectedRefereeLabel);
            this.Home.Location = new System.Drawing.Point(4, 25);
            this.Home.Name = "Home";
            this.Home.Padding = new System.Windows.Forms.Padding(3);
            this.Home.Size = new System.Drawing.Size(792, 501);
            this.Home.TabIndex = 0;
            this.Home.Text = "tabPage1";
            this.Home.UseVisualStyleBackColor = true;
            // 
            // addResultPanel
            // 
            this.addResultPanel.Controls.Add(this.button1);
            this.addResultPanel.Controls.Add(this.dateTimePicker);
            this.addResultPanel.Controls.Add(this.label2);
            this.addResultPanel.Controls.Add(this.pointsTextBox);
            this.addResultPanel.Controls.Add(this.label1);
            this.addResultPanel.Location = new System.Drawing.Point(519, 161);
            this.addResultPanel.Name = "addResultPanel";
            this.addResultPanel.Size = new System.Drawing.Size(267, 148);
            this.addResultPanel.TabIndex = 4;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(61, 117);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(151, 23);
            this.button1.TabIndex = 4;
            this.button1.Text = "Add";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.AddResultToDatabase);
            // 
            // dateTimePicker
            // 
            this.dateTimePicker.Location = new System.Drawing.Point(3, 89);
            this.dateTimePicker.Name = "dateTimePicker";
            this.dateTimePicker.Size = new System.Drawing.Size(261, 22);
            this.dateTimePicker.TabIndex = 3;
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(61, 63);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(151, 23);
            this.label2.TabIndex = 2;
            this.label2.Text = "Date";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // pointsTextBox
            // 
            this.pointsTextBox.Location = new System.Drawing.Point(61, 38);
            this.pointsTextBox.Name = "pointsTextBox";
            this.pointsTextBox.Size = new System.Drawing.Size(151, 22);
            this.pointsTextBox.TabIndex = 1;
            this.pointsTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(61, 12);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(151, 23);
            this.label1.TabIndex = 0;
            this.label1.Text = "Points";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // addButton
            // 
            this.addButton.Location = new System.Drawing.Point(580, 132);
            this.addButton.Name = "addButton";
            this.addButton.Size = new System.Drawing.Size(151, 23);
            this.addButton.TabIndex = 3;
            this.addButton.Text = "Add Result";
            this.addButton.UseVisualStyleBackColor = true;
            this.addButton.Click += new System.EventHandler(this.AddButtonAction);
            // 
            // participantsDataGridView
            // 
            this.participantsDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.participantsDataGridView.Location = new System.Drawing.Point(39, 92);
            this.participantsDataGridView.Name = "participantsDataGridView";
            this.participantsDataGridView.RowTemplate.Height = 24;
            this.participantsDataGridView.Size = new System.Drawing.Size(474, 360);
            this.participantsDataGridView.TabIndex = 2;
            // 
            // selectedRefereeLabel
            // 
            this.selectedRefereeLabel.Location = new System.Drawing.Point(44, 27);
            this.selectedRefereeLabel.Name = "selectedRefereeLabel";
            this.selectedRefereeLabel.Size = new System.Drawing.Size(242, 23);
            this.selectedRefereeLabel.TabIndex = 1;
            this.selectedRefereeLabel.Text = "label1";
            // 
            // tabPage2
            // 
            this.Raport.Controls.Add(this.label3);
            this.Raport.Controls.Add(this.raportDataGridView);
            this.Raport.Location = new System.Drawing.Point(4, 25);
            this.Raport.Name = "Raport";
            this.Raport.Padding = new System.Windows.Forms.Padding(3);
            this.Raport.Size = new System.Drawing.Size(792, 501);
            this.Raport.TabIndex = 1;
            this.Raport.Text = "tabPage2";
            this.Raport.UseVisualStyleBackColor = true;
            // 
            // label3
            // 
            this.label3.Location = new System.Drawing.Point(59, 23);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(255, 23);
            this.label3.TabIndex = 1;
            this.label3.Text = "Raport";
            // 
            // raportDataGridView
            // 
            this.raportDataGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.raportDataGridView.Location = new System.Drawing.Point(33, 68);
            this.raportDataGridView.Name = "raportDataGridView";
            this.raportDataGridView.RowTemplate.Height = 24;
            this.raportDataGridView.Size = new System.Drawing.Size(489, 386);
            this.raportDataGridView.TabIndex = 0;
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(634, 10);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(116, 23);
            this.button2.TabIndex = 1;
            this.button2.Text = "Logout";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.Logout);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(834, 554);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.tabControl1);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "MainForm";
            this.Text = "MainForm";
            this.tabControl1.ResumeLayout(false);
            this.Home.ResumeLayout(false);
            this.addResultPanel.ResumeLayout(false);
            this.addResultPanel.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.participantsDataGridView)).EndInit();
            this.Raport.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.raportDataGridView)).EndInit();
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.Button button2;

        private System.Windows.Forms.Label label3;

        private System.Windows.Forms.DataGridView raportDataGridView;

        private System.Windows.Forms.Button button1;

        private System.Windows.Forms.DateTimePicker dateTimePicker;

        private System.Windows.Forms.Label label2;

        private System.Windows.Forms.TextBox pointsTextBox;

        private System.Windows.Forms.Button addButton;
        private System.Windows.Forms.Panel addResultPanel;
        private System.Windows.Forms.Label label1;

        private System.Windows.Forms.DataGridView participantsDataGridView;

        private System.Windows.Forms.Label selectedRefereeLabel;

        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage Home;
        private System.Windows.Forms.TabPage Raport;

        #endregion
    }
}
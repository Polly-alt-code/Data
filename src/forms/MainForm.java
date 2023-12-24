package forms;

import code.OrderBuilder;
import code.Product;
import code.Extensions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainForm extends JFrame {

    private JPanel mainPanel;
    private JTable tableProducts;
    private JButton btnOrder;
    private JComboBox<String> comboBoxDelivery;
    private DefaultTableModel tableModel;
    private final OrderBuilder orderBuilder = new OrderBuilder();

    public MainForm(String header) {
        super(header);

        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setMinimumSize(mainPanel.getSize());
        setSize(new Dimension(700,400));

        // center on the screen
        setLocationRelativeTo(null);



        setUpTable();
        setUpDeliveryComboBox();
        setUpOrderButton();
    }

    // Кнопка "Собрать заказ"
    private void setUpOrderButton() {
        btnOrder.addActionListener(e -> {
            orderBuilder.clear(); // Очищаем старый заказ

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String productName = (String) tableModel.getValueAt(i, 0);
                int price = Extensions.tryParseInt(tableModel.getValueAt(i, 1), -1);
                int count = Extensions.tryParseInt(tableModel.getValueAt(i, 2), -1);

                orderBuilder.addProduct(productName, price, count);
            }

            String deliveryMethodName = getDeliveryName(comboBoxDelivery.getSelectedIndex());
            int deliveryMethodPrice = getDeliveryPrice(comboBoxDelivery.getSelectedIndex());

            String order = orderBuilder.changeDeliveryMethod(deliveryMethodName, deliveryMethodPrice)
                    .build();

            JOptionPane.showMessageDialog(mainPanel, "Ваш заказ:\n" + order);
        });
    }

    private String getDeliveryName(int deliveryMethodIndex) {
        switch (deliveryMethodIndex) {
            case 1 -> { return "Обычная"; }
            case 2 -> { return "Быстрая"; }
            default -> { return ""; }
        }
    }

    private int getDeliveryPrice(int deliveryMethodIndex) {
        switch (deliveryMethodIndex) {
            case 1 -> { return 100; }
            case 2 -> { return 200; }
            default -> { return 0; }
        }
    }

    private void setUpDeliveryComboBox() {
        comboBoxDelivery.addItem("Самовывоз (бесплатно)");
        comboBoxDelivery.addItem("Обычная (100р)");
        comboBoxDelivery.addItem("Быстрая (200р)");
    }

    // Настройка таблицы с продуктами
    private void setUpTable() {
        tableModel = new DefaultTableModel(
                null,
                new String[] { "Продукт", "Цена", "Кол-во" } // Названия столбцов
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Вводить данные можно только во столбец с кол-вом товара
            }
        };
        tableProducts.setModel(tableModel);
        addProductsToTable();
    }

    private void addProductsToTable() {
        tableModel.setNumRows(0); // Очистка всех строк на всякий случай

        Product[] products = getProducts();

        for (var product : products)
            tableModel.addRow(new String[] {
                    product.name, String.valueOf(product.price), "0"
            });
    }

    private Product[] getProducts() {
        return new Product[] {
                new Product("Молоко", 75),
                new Product("Шоколадка", 55),
                new Product("Курочка", 250),
                new Product("Печенье", 88),
                new Product("Лимонад", 99),
                new Product("Хлеб", 33),
                new Product("Булочка", 42),
                new Product("Сметана", 70),
                new Product("Йогурт", 50),
                new Product("Сыр", 200),
                new Product("Майонез", 99),
                new Product("Масло", 140),
        };
    }
}

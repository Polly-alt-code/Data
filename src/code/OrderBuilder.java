package code;

public class OrderBuilder {

    private final StringBuilder order = new StringBuilder();
    private final StringBuilder deliveryMethod = new StringBuilder();
    private int totalCost;
    private int deliveryPrice;

    public OrderBuilder addProduct(String name, int price, int count) {
        if (count <= 0 || price <= 0)
            return this;

        // Добавить продукт в формате:
        // Молоко x2 = 120р
        // \n - переход на следующую строку
        order.append(name)
                .append(" x")
                .append(count)
                .append(" = ")
                .append(price * count)
                .append('р')
                .append('\n');

        totalCost += price * count;
        return this;
    }

    public OrderBuilder changeDeliveryMethod(String name, int price) {
        if (name.isEmpty()) {
            deliveryPrice = 0;
            return this;
        }

        deliveryMethod.setLength(0); // удаление старого метода доставки
        deliveryMethod.append(name)
                .append(" = ")
                .append(price)
                .append('р')
                .append('\n');

        deliveryPrice = price;
        return this;
    }

    public String build() {
        if (order.isEmpty())
            return "Заказ пуст";

        if (deliveryMethod.isEmpty())
            deliveryMethod.append("Самовывоз = 0р\n");

        return order + "\nДоставка:\n" + deliveryMethod + "======\n" + "Всего: " + (totalCost + deliveryPrice) + "р";
    }

    public void clear() {
        order.setLength(0);
        deliveryMethod.setLength(0);
        totalCost = 0;
    }
}

package edu.gatech.cs6310;
import java.util.*;

public class DeliveryService {
    final Map<String, Store> stores = new TreeMap<>();
    final Map<String, Pilot> pilots = new TreeMap<>();
    final Set<String> licenses =  new HashSet<>();
    final Map<String, Customer> customers = new TreeMap<>();

    public void commandLoop() {
        Scanner commandLineInput = new Scanner(System.in);
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (true) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.println("> " + wholeInputLine);

                if (tokens[0].equals("make_store")) {
                    if(storeExists(tokens[1])) {
                        System.out.println("ERROR:store_identifier_already_exists");
                    } else {
                        stores.put(tokens[1], new Store(tokens[1], Integer.parseInt(tokens[2])));
                        System.out.println("OK:change_completed");
                    }
                } else if (tokens[0].equals("display_stores")) {
                   for(String key : stores.keySet())
                        stores.get(key).displayStore();
                    System.out.println("OK:display_completed");
                } else if (tokens[0].equals("sell_item")) {
                    if(storeExists(tokens[1])) {
                        if(stores.get(tokens[1]).getItems().containsKey(tokens[2])) {
                            System.out.println("ERROR:item_identifier_already_exists");
                        } else {
                            stores.get(tokens[1]).addItem(new Item(tokens[2], Integer.parseInt(tokens[3])));
                            System.out.println("OK:change_completed");
                        }
                    } else {
                        printStoreDoesntExist();
                    }
                } else if (tokens[0].equals("display_items")) {
                    if(storeExists(tokens[1])) {
                        for(String key : stores.get(tokens[1]).getItems().keySet())
                            stores.get(tokens[1]).getItems().get(key).displayItem();
                        System.out.println("OK:display_completed");
                    } else {
                        printStoreDoesntExist();
                    }
                } else if (tokens[0].equals("make_pilot")) {
                    if(!pilots.containsKey(tokens[1])) {
                        if (!licenses.contains(tokens[6])) {
                            licenses.add(tokens[6]);
                            pilots.put(tokens[1], new Pilot(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], Integer.parseInt(tokens[7])));
                            System.out.println("OK:change_completed");
                        } else {
                            System.out.println("ERROR:pilot_license_already_exists");
                        }
                    } else {
                        System.out.println("ERROR:pilot_identifier_already_exists");
                    }
                } else if (tokens[0].equals("display_pilots")) {
                    for(String key : pilots.keySet())
                        pilots.get(key).displayPilot();
                    System.out.println("OK:display_completed");
                } else if (tokens[0].equals("make_drone")) {
                    if(storeExists(tokens[1])) {
                        int droneId = Integer.parseInt(tokens[2]);
                        if(!stores.get(tokens[1]).getDrones().containsKey(droneId)) {
                            stores.get(tokens[1]).addDrone(new Drone(droneId, Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4])));
                            System.out.println("OK:change_completed");
                        } else {
                            System.out.println("ERROR:drone_identifier_already_exists");
                        }
                    } else {
                        printStoreDoesntExist();
                    }
                } else if (tokens[0].equals("display_drones")) {
                    if(storeExists(tokens[1])) {
                        for(Integer key : stores.get(tokens[1]).getDrones().keySet())
                            stores.get(tokens[1]).getDrones().get(key).displayDrone();
                        System.out.println("OK:display_completed");
                    } else {
                        printStoreDoesntExist();
                    }
                } else if (tokens[0].equals("fly_drone")) {
                    if(storeExists(tokens[1])) {
                        if(stores.get(tokens[1]).getDrones().containsKey(Integer.parseInt(tokens[2]))) {
                            if (pilots.containsKey(tokens[3])) {

                                // Reset pilot if already assigned to a drone
                                for(String store: stores.keySet()) {
                                    for(Integer drone : stores.get(store).getDrones().keySet()) {
                                        if(stores.get(store).getDrones().get(drone).getCurrentPilot() == null)
                                            continue;
                                        if(stores.get(store).getDrones().get(drone).getCurrentPilot().username.equals(tokens[3])) {
                                            stores.get(store).getDrones().get(drone).setCurrentPilot(null);
                                            break;
                                        }
                                    }
                                }

                                stores.get(tokens[1]).getDrones().get(Integer.parseInt(tokens[2])).setCurrentPilot(pilots.get(tokens[3]));
                                System.out.println("OK:change_completed");
                            } else {
                                System.out.println("ERROR:pilot_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:drone_identifier_does_not_exist");
                        }
                    } else {
                        printStoreDoesntExist();
                    }
                } else if (tokens[0].equals("make_customer")) {
                    if(!customers.containsKey(tokens[1])) {
                        customers.put(tokens[1], new Customer(tokens[1], tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6])));
                        System.out.println("OK:change_completed");
                    } else {
                        System.out.println("ERROR:customer_identifier_already_exists");
                    }
                } else if (tokens[0].equals("display_customers")) {
                    for(String key : customers.keySet())
                        customers.get(key).displayCustomer();
                    System.out.println("OK:display_completed");
                } else if (tokens[0].equals("start_order")) {
                    if(storeExists(tokens[1])) {
                        if(customers.containsKey(tokens[4])) {
                            if (stores.get(tokens[1]).getDrones().containsKey(Integer.parseInt(tokens[3]))) {
                                // Create a order entry
                                if (!stores.get(tokens[1]).getOrders().containsKey(tokens[2])) {
                                    stores.get(tokens[1]).addOrder(new Order(tokens[2], tokens[4], Integer.parseInt(tokens[3])));
                                    System.out.println("OK:change_completed");
                                } else {
                                    System.out.println("ERROR:order_identifier_already_exists");
                                }
                            } else {
                                System.out.println("ERROR:drone_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:customer_identifier_does_not_exist");
                        }
                    } else {
                        printStoreDoesntExist();
                    }
                } else if (tokens[0].equals("display_orders")) {
                    if(storeExists(tokens[1])) {
                        for(String key: stores.get(tokens[1]).getOrders().keySet())
                            stores.get(tokens[1]).getOrders().get(key).displayOrder();
                        System.out.println("OK:display_completed");
                    } else {
                        printStoreDoesntExist();
                    }
                } else if (tokens[0].equals("request_item")) {
                    if(storeExists(tokens[1])) {
                        if(stores.get(tokens[1]).getOrders().containsKey(tokens[2])) {
                            if(stores.get(tokens[1]).getItems().containsKey(tokens[3])) {
                                // Get order
                                Order order = stores.get(tokens[1]).getOrders().get(tokens[2]);
                                if(order.itemExists(order, tokens[2], tokens[3])) {
                                    System.out.println("ERROR:item_already_ordered");
                                    continue;
                                }

                                // Get customer
                                Customer customer = customers.get(order.getCustomerId());
                                Integer qty = Integer.parseInt(tokens[4]);
                                Integer price = Integer.parseInt(tokens[5]);
                                int updatedCredit = customer.getCredit() - order.getCost() - (qty * price);

                                // Get drone
                                Drone drone = stores.get(tokens[1]).getDrones().get(order.getDroneId());
                                Integer weight = stores.get(tokens[1]).getItems().get(tokens[3]).getWeight();
                                int updatedCapacity = drone.getRemainingCapacity() - order.getWeight() - (qty * weight);

                                if(updatedCredit < 0) {
                                    System.out.println("ERROR:customer_cant_afford_new_item");
                                    continue;
                                }

                                if(updatedCapacity < 0) {
                                    System.out.println("ERROR:drone_cant_carry_new_item");
                                    continue;
                                }

                                // Check constraints & update order
                                if (updatedCredit >= 0 && updatedCapacity >= 0) {
                                    Line line = new Line(tokens[3], qty, price, weight);
                                    order.addLine(line);
                                    drone.setRemainingCapacity(drone.getRemainingCapacity()-line.getTotalWeight());
                                    drone.addOrder(order.getId());
                                }
                                System.out.println("OK:change_completed");
                            } else {
                                System.out.println("ERROR:item_identifier_does_not_exist");
                            }
                        } else {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        }
                    } else {
                        printStoreDoesntExist();
                    }
                } else if (tokens[0].equals("purchase_order")) {
                    if(storeExists(tokens[1])) {
                        if(stores.get(tokens[1]).getOrders().containsKey(tokens[2])) {
                            Store store = stores.get(tokens[1]);
                            Order order = store.getOrders().get(tokens[2]);
                            Drone drone = store.getDrones().get(order.getDroneId());
                            Customer customer = customers.get(order.getCustomerId());

                            if(drone.getTripsRemaining() == 0) {
                                System.out.println("ERROR:drone_needs_fuel");
                                continue;
                            }

                            if (drone.getCurrentPilot() == null) {
                                System.out.println("ERROR:drone_needs_pilot");
                                continue;
                            }

                            // Deducting customer credit & adding to store revenue
                            for (Line line : order.getLines()) {
                                store.setRevenue(store.getRevenue() + line.getTotalPrice());
                                customer.setCredit(customer.getCredit() - line.getTotalPrice());
                            }
                            drone.setRemainingCapacity(drone.getRemainingCapacity() + order.getWeight());
                            drone.removeOrder(order.getId());

                            // Updating drone remaining deliveries
                            drone.completeOrder();

                            // Updating pilot experience
                            pilots.get(drone.getCurrentPilot().username).incrementExperience();
                            store.getOrders().remove(tokens[2]);
                            System.out.println("OK:change_completed");
                        } else {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        }
                    } else {
                        printStoreDoesntExist();
                    }
                } else if (tokens[0].equals("cancel_order")) {
                    if(storeExists(tokens[1])) {
                        if(stores.get(tokens[1]).getOrders().containsKey(tokens[2])) {
                            Drone drone = stores.get(tokens[1]).getDrones().get(stores.get(tokens[1]).getOrders().get(tokens[2]).getDroneId());
                            drone.removeOrder(tokens[2]);
                            drone.setRemainingCapacity(drone.getRemainingCapacity()+  stores.get(tokens[1]).getOrders().get(tokens[2]).getWeight());
                            stores.get(tokens[1]).getOrders().remove(tokens[2]);
                            System.out.println("OK:change_completed");
                        } else {
                            System.out.println("ERROR:order_identifier_does_not_exist");
                        }
                    } else {
                        printStoreDoesntExist();
                    }
                } else if (tokens[0].equals("stop")) {
                    System.out.println("stop acknowledged");
                    break;

                } else {
                    System.out.println("command " + tokens[0] + " NOT acknowledged");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }

        System.out.println("simulation terminated");
        commandLineInput.close();
    }

    private void printStoreDoesntExist() {
        System.out.println("ERROR:store_identifier_does_not_exist");
    }

    private boolean storeExists(String storeName) {
        return stores.containsKey(storeName);
    }
}

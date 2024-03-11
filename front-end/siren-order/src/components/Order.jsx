import { useState } from "react";

const Order = () => {

    const [name, setName] = useState("coffee-siren-order");

    return (
        <div className="order">
            <header className="order-header">
                <div>{name}</div>
            </header>
        </div>
    )

}


export default Order;
const Main = () => {
    return (
        <main className="main-page container">
            <div className="main-page-content">
                <h1>COFFEE-SIREN-ORDER</h1>
                <p>
                    커피를 주문하고 받아보세오.
                </p>
                <div className="order-btn">
                    <button>Order</button>
                    <button className="secondary-btn">Menu</button>
                </div>

                <div className="source-code">
                    <p>You can see Details Here</p>

                    <div className="icons">
                        <img className="bottom-icons" src="/images/github_image.jpeg" alt="" />
                        <img className="bottom-icons" src="/images/tistory_image.png" alt="" />
                    </div>
                </div>
            </div>
            <div className="main-page-image">
                <img className="coffee-image" src="/images/coffee1.png" alt="coffee-logo" />
            </div>
        </main>
    )
};

export default Main;
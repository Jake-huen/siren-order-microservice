import React from "react";
import {FaCoffee} from 'react-icons/fa';

const Navigation = () => {
    return (
        <>
        <nav className="header">
            <div className="top_header">
                <div className="icon">
                    {/* <img className="top-logo" src="/images/brand_logo.png" alt="" /> */}
                    <FaCoffee />
                </div>
                <div className="info">
                    <p>COFFEE SIREN ORDER</p>
                </div>
            </div>
            

            <ul>
                <li href="#">Menu</li>
                <li href="#">Location</li>
                <li href="#">About</li>
                <li href="#">Contact</li>
            </ul>

            <button>login</button>
        </nav>
        </>
        
    );

}

export default Navigation;
import React, { Component } from "react";
import "./Product.css";

export default class ReportUsers extends Component {
    constructor(props) {
        super(props);

        this.state = {
            users: []
        };
    }

    componentDidMount() {
        fetch('/management/reports/users', {
            credentials: "same-origin"
        })
            .then(results => {
                return results.json();
            }).then(data => {
                let users = data.data;
                this.setState({users: users});
            }
        )
    }

    render() {
        let users = this.state.users;

        return (
            <div>
                <h1>Käyttäjäraportit</h1>
                <div className="product">
                    {
                        users.map((result, i) => {
                            return(
                                <div key={i}>
                                    <p>Id: {result.id}</p>
                                    <p>Etunimi: {result.firstName}</p>
                                    <p>Sukunimi: {result.lastName}</p>
                                    <p>Osoite: {result.address}</p>
                                    <p>Sähköposti: {result.email}</p>
                                    <p>Puhelinnumero: {result.phoneNumber}</p>
                                </div>
                            )
                        })
                    }
                </div>
            </div>
        );
    }
}
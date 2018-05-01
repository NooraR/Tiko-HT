import React, { Component } from "react";
import { Table } from 'react-bootstrap';
import "./ReportUsers.css";

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
            <div className="usersContainer">
                <h2>Käyttäjäraportit</h2>
                <div>
                    {
                        users.map((result, i) => {
                            return(
                                <div className="users" key={i}>
                                    <h3>{result.user.firstName} {result.user.lastName}</h3>
                                    <Table responsive>
                                        <tbody>
                                            <tr><td>Id: {result.user.id}</td></tr>
                                            <tr><td>Etunimi: {result.user.firstName}</td></tr>
                                            <tr><td>Sukunimi: {result.user.lastName}</td></tr>
                                            <tr><td>Osoite: {result.user.address}</td></tr>
                                            <tr><td>Sähköposti: {result.user.email}</td></tr>
                                            <tr><td>Puhelinnumero: {result.user.phoneNumber}</td></tr>
                                            <tr><td>Vuonna {result.year} ostettujen tuotteiden määrä: {result.numberOfProducts}</td></tr>
                                        </tbody>
                                    </Table>
                                </div>
                            )
                        })
                    }
                </div>
            </div>
        );
    }
}
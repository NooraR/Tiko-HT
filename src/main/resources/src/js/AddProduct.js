import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel } from "react-bootstrap";
import "./AddProduct.css";

export default class AddProduct extends Component {
    constructor(props) {
        super(props);

        this.state = {
            name: "",
            author: "",
            isbn: "",
            published: "",
            genre: "",
            weight: 0
        };
    }

    validateForm() {
        return this.state.name.length > 0
            && this.state.auchtor.length > 0
            && this.state.isbn.length > 0
            && this.state.published.length > 0
            && this.state.genre.length > 0
            && this.state.weight > 0;
    }

    handleChange(event) {
        this.setState({
            [event.target.id]: event.target.value
        });
    }

    handleSubmit(event) {
        event.preventDefault();
    }

    render() {
        return (
            <div className="container">
                <h2>Uuden teoksen lisääminen</h2>
                <form onSubmit={this.handleSubmit}>
                    <FormGroup controlId="firstName" bsSize="large">
                        <ControlLabel>Nimi*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.name}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup controlId="auchtor" bsSize="large">
                        <ControlLabel>Tekijä*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.auchtor}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup controlId="isbn" bsSize="large">
                        <ControlLabel>ISBN*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.isbn}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup controlId="published" bsSize="large">
                        <ControlLabel>Julkaisuvuosi (VVVV)*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.published}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup controlId="genre" bsSize="large">
                        <ControlLabel>Genre*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="email"
                            value={this.state.genre}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup controlId="weight" bsSize="large">
                        <ControlLabel>Paino*</ControlLabel>
                        <FormControl
                            value={this.state.weight}
                            onChange={this.handleChange}
                            type="text"
                        />
                    </FormGroup>
                    <p>*:llä merkityt kohdat ovat pakollisia</p>
                    <Button
                        block
                        bsSize="large"
                        disabled={!this.validateForm()}
                        type="submit"
                    >
                        Lisää teos
                    </Button>
                </form>
            </div>
        );
    }
}
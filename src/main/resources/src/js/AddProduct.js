import React, { Component } from "react";
import { Button, FormGroup, FormControl, ControlLabel, Alert } from "react-bootstrap";
import "./AddProduct.css";

export default class AddProduct extends Component {
    constructor(props) {
        super(props);

        this.state = {
            sellingPrice: 0,
            purchasePrice: 0,
            name: "",
            author: "",
            isbn: "",
            published: "",
            genre: "",
            type: "",
            weight: 0,
            antiquaries: [],
            antiquary: 0
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    validateForm() {
        return this.state.name.length > 0
            && this.state.author.length > 0
            && this.state.weight > 0;
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });
    };

    componentDidMount() {
        fetch('/data/antiquaries')
            .then(results => {
                return results.json();
            }).then(data => {
                let antiquaries = data.data;
                this.setState({antiquaries: antiquaries});
            }
        )
    }

    handleSubmit(event) {
        event.preventDefault();
        fetch("/management/product/add", {
            method: 'POST',
            headers: {"Content-type": "application/json; charset=UTF-8"},
            body: JSON.stringify({
                "status": "FREE",
                "sellingPrice": this.state.sellingPrice,
                "purchasePrice": this.state.purchasePrice,
                "work": {
                    "name": this.state.name,
                    "author": this.state.author,
                    "isbn": this.state.isbn,
                    "published": this.state.published,
                    "genre": this.state.genre,
                    "type": this.state.type,
                    "weight": this.state.weight
                },
                "antiquary": {
                    "id": this.state.antiquary
                }
            }),
            credentials: "same-origin"
        })
            .then(result => {
                return result.json();
            })
    .then(json => {
            if(json.success) {
                /*
                this.props.alert("Tuotteen lisäys onnistui!")
                 */
            } else {
                /*
                this.props.alert("Tuotteen lisäys epäonnistui");
                 */
            }
        });
    }

    render() {
        let antiquaries = this.state.antiquaries;

        return (
            <div className="addProduct">
                <h2>Uuden teoksen lisääminen</h2>
                <form onSubmit={this.handleSubmit}>
                    <FormGroup className="formGroup" controlId="sellingPrice" bsSize="large">
                        <ControlLabel>Myyntihinta (euroa)*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="number"
                            value={this.state.sellingPrice}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="purchasePrice" bsSize="large">
                        <ControlLabel>Ostohinta (euroa)*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="number"
                            value={this.state.purchasePrice}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="name" bsSize="large">
                        <ControlLabel>Nimi*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.name}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="author" bsSize="large">
                        <ControlLabel>Tekijä*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.author}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="isbn" bsSize="large">
                        <ControlLabel>ISBN</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.isbn}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="published" bsSize="large">
                        <ControlLabel>Julkaisuvuosi (VVVV)</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.published}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="genre" bsSize="large">
                        <ControlLabel>Genre</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.genre}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="type" bsSize="large">
                        <ControlLabel>Tyyppi</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.type}
                            onChange={this.handleChange}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="weight" bsSize="large">
                        <ControlLabel>Paino* (kg)</ControlLabel>
                        <FormControl
                            value={this.state.weight}
                            onChange={this.handleChange}
                            type="number"
                        />
                    </FormGroup>
                    <FormGroup controlId="antiquary">
                        <ControlLabel>Antikvariaatti*</ControlLabel>
                        <FormControl
                            className="antiquary"
                            componentClass="select"
                            placeholder="Valitse..."
                            value={this.state.antiquary}
                            onChange={this.handleChange}
                        >
                            {
                                antiquaries.map((result, i) => {
                                    return(
                                        <option key={i} value={result.id}>{result.name}</option>
                                    )

                                })
                            }
                        </FormControl>
                    </FormGroup>
                    <p>*:llä merkityt kohdat ovat pakollisia</p>
                    <Button
                        block
                        bsSize="large"
                        bsStyle="success"
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
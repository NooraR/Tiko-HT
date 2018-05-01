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
            antiquary: 1,
            allWorks: [],
            work: "",
            newWork: true
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleWorkChange = this.handleWorkChange.bind(this);
    }

    validateForm() {
        return this.state.name.length > 0
            && this.state.author.length > 0
            && this.state.weight > 0;
    }

    handleWorkChange(id) {
        if(id) {
            id = parseInt(id);
            let work = this.state.allWorks.find((item) => {
                return item.id === id;
            });

            this.setState({
                newWork: false,
                name: work.name,
                author: work.author,
                isbn: work.isbn,
                published: work.published,
                genre: work.genre,
                type: work.type,
                weight: work.weight
            });
        }
    }

    handleChange = event => {
        this.setState({
            [event.target.id]: event.target.value
        });

        if(event.target.id === "work") {
            this.handleWorkChange(event.target.value);
        }
    };

    componentDidMount() {
        fetch('/data/antiquaries')
            .then(results => {
                return results.json();
            }).then(data => {
                let antiquaries = data.data;
                this.setState({antiquaries: antiquaries});
            }
        );

        fetch("/data/works/all")
            .then(results => {
                return results.json();
            }).then(data => {
                this.setState({
                    allWorks: data.data
                });
            });
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
                this.props.showConfirmation(true, "Tuote lisätty", "Tuotteen lisäys onnistui!");
                this.props.refreshWorks();
            } else {
                this.props.showConfirmation(true, "Tuottetta ei lisätty", "Tuotteen lisäys epäonnistui!" + json.message);
            }
        });
    }

    render() {
        let antiquaries = this.state.antiquaries;

        return (
            <div className="addProduct">
                <h2>Tuotteen lisäys</h2>
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
                    <FormGroup controlId="work">
                        <ControlLabel>Valitse teos</ControlLabel>
                        <FormControl
                            className="work"
                            componentClass="select"
                            value={this.state.work}
                            onChange={this.handleChange}
                        >
                            <option value="">Valitse...</option>
                            {
                                this.state.allWorks.map((result, i) => {
                                    return(
                                        <option key={i} value={result.id}>{result.name}, {result.author}</option>
                                    )

                                })
                            }
                        </FormControl>
                    </FormGroup>
                    <h3><strong>TAI</strong> Syötä teoksen tiedot</h3>
                    <FormGroup className="formGroup" controlId="name" bsSize="large">
                        <ControlLabel>Nimi*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.name}
                            onChange={this.handleChange}
                            disabled={!this.state.newWork}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="author" bsSize="large">
                        <ControlLabel>Tekijä*</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.author}
                            onChange={this.handleChange}
                            disabled={!this.state.newWork}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="isbn" bsSize="large">
                        <ControlLabel>ISBN</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.isbn}
                            onChange={this.handleChange}
                            disabled={!this.state.newWork}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="published" bsSize="large">
                        <ControlLabel>Julkaisuvuosi (VVVV)</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.published}
                            onChange={this.handleChange}
                            disabled={!this.state.newWork}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="genre" bsSize="large">
                        <ControlLabel>Genre</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.genre}
                            onChange={this.handleChange}
                            disabled={!this.state.newWork}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="type" bsSize="large">
                        <ControlLabel>Tyyppi</ControlLabel>
                        <FormControl
                            autoFocus
                            type="text"
                            value={this.state.type}
                            onChange={this.handleChange}
                            disabled={!this.state.newWork}
                        />
                    </FormGroup>
                    <FormGroup className="formGroup" controlId="weight" bsSize="large">
                        <ControlLabel>Paino* (kg)</ControlLabel>
                        <FormControl
                            value={this.state.weight}
                            onChange={this.handleChange}
                            type="number"
                            disabled={!this.state.newWork}
                        />
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
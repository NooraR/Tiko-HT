import React, { Component } from "react";
import { Button, FormGroup, FormControl } from "react-bootstrap";
import Product from './Product.js';
import "./Search.css";

export default class Search extends Component {
    constructor() {
        super();

        this.state = {
            works: [],
        };
    }

    componentDidMount() {
        fetch('http://localhost/data/works')
            .then(results => {
                return results.json()
            }).then(data => {
                console.log("Mitä dataa tulee", data.data);
        })
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
            <div className="SearchContainer">
                <div className="Search">
                    <form onSubmit={this.handleSubmit}>
                        <FormGroup className="FormGroup" bsSize="large">
                            <FormControl
                                autoFocus
                                placeholder="Search.."
                                type="text"
                                value={this.state.search}
                                onChange={this.handleChange}
                            />
                        </FormGroup>
                        <Button
                            block
                            bsSize="large"
                            type="submit"
                        >
                            Hae
                        </Button>
                    </form>
                </div>
                <div className="ResultContainer">
                    { this.state.works.forEach(result => {
                        return (<Product name={result.name} auchtor={result.auchtor} isbn={result.isbn} published={result.published} genre={result.genre} weight={result.weight} />)
                    })
                    }
                </div>
            </div>
        );
    }
}
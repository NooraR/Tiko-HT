export class searchService {

    getAllData(): any {
        return this.http.get(`/data/works`)
            .map((res: Response) => res.json());
    }
}
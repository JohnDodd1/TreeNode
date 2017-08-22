package tomsksoft.jd.managertree.ui

import tomsksoft.jd.managertree.app.DataProvider

class Presenter(val view: IView) {
    fun onCreate() {
        val iterator = DataProvider.company.headComponent.iterator()
        while (iterator.hasNext()) {
            iterator.next().log()
        }

    }


}
function IDList = getIDList()
%getIDList Get the IDs of the polygons in the network.
%   IDList = getIDList() Returns a cell array of strings containing the IDs
%   of the polygons in the SUMO network.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getIDList.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
IDList = traci.polygon.getUniversal(constants.ID_LIST, '');
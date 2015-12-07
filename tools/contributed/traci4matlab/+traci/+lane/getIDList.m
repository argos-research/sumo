function IDList = getIDList()
%getIDList Get the IDs of the lanes in the network.
%   IDList = getIDList() Returns a cell array of strings containing the IDs
%   of the lanes in the SUMO network.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
IDList = traci.lane.getUniversal(constants.ID_LIST, '');
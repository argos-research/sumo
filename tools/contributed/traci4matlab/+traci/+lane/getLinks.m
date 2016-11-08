function links = getLinks(laneID)
%getLinks Returns a cell containing ids of successor lanes together with
%priority, open and foe.
%   links = getLinks(LANEID) Returns a cell containing ids of successor 
%   lanes together with priority, open and foe.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLinks.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
links = traci.lane.getUniversal(constants.LANE_LINKS, laneID);
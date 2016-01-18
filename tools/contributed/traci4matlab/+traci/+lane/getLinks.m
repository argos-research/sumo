function links = getLinks(laneID)
%getLinks Returns a cell containing ids of successor lanes together with
%priority, open and foe.
%   links = getLinks(LANEID) Returns a cell containing ids of successor 
%   lanes together with priority, open and foe.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLinks.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
links = traci.lane.getUniversal(constants.LANE_LINKS, laneID);
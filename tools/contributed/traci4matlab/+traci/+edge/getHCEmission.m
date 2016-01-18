function HCEmission = getHCEmission(edgeID)
%getHCEmission Returns the HC emission on the given edge.
%   HCEmission = getCOEmission(EDGEID) Returns the HC emission in mg for 
%   the last time step on the given edge.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getHCEmission.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
HCEmission = traci.edge.getUniversal(constants.VAR_HCEMISSION, edgeID);
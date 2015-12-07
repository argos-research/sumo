function minGap = getMinGap(vehID)
%getMinGap Returns the gap to front vehicle.
%   minGap = getMinGap(VEHID) Returns the offset (gap to front vehicle if 
%   halting) of this vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
minGap = traci.vehicle.getUniversal(constants.VAR_MINGAP, vehID);